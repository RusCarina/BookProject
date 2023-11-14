package repository.ebook;

import model.EBook;
import model.builder.EBookBuilder;
import repository.ebook.EBookRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EBookRepositoryMySQL implements EBookRepository {

    private final Connection connection;

    public EBookRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<EBook> findAll() {
        String sql = "SELECT * FROM ebook;";

        List<EBook> ebooks = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                ebooks.add(getEBookFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ebooks;
    }

    @Override
    public Optional<EBook> findById(Long id) {
        String sql = "SELECT * FROM ebook WHERE id = ?";
        Optional<EBook> ebook = Optional.empty();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                ebook = Optional.of(getEBookFromResultSet(resultSet));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return ebook;
    }

    /**
     *
     * How to reproduce a sql injection attack on insert statement
     *
     *
     * 1) Uncomment the lines below and comment out the PreparedStatement part
     * 2) For the Insert Statement DROP TABLE SQL Injection attack to succeed we will need multi query support to be added to our connection
     * Add to JDBConnectionWrapper the following flag after the DB_URL + schema concatenation: + "?allowMultiQueries=true"
     * 3) book.setAuthor("', '', null); DROP TABLE book; -- "); // this will delete the table book
     * 3*) book.setAuthor("', '', null); SET FOREIGN_KEY_CHECKS = 0; SET GROUP_CONCAT_MAX_LEN=32768; SET @tables = NULL; SELECT GROUP_CONCAT('`', table_name, '`') INTO @tables FROM information_schema.tables WHERE table_schema = (SELECT DATABASE()); SELECT IFNULL(@tables,'dummy') INTO @tables; SET @tables = CONCAT('DROP TABLE IF EXISTS ', @tables); PREPARE stmt FROM @tables; EXECUTE stmt; DEALLOCATE PREPARE stmt; SET FOREIGN_KEY_CHECKS = 1; --"); // this will delete all tables. You are not required to know the table name anymore.
     * 4) Run the program. You will get an exception on findAll() method because the test_library.book table does not exist anymore
     */


    // ALWAYS use PreparedStatement when USER INPUT DATA is present
    // DON'T CONCATENATE Strings!

    @Override
    public boolean save(EBook ebook) {
        String sql = "INSERT INTO ebook VALUES(null, ?, ?, ?, ?);";

        String newSql = "INSERT INTO ebook VALUES(null, \'" + ebook.getAuthor() +"\', \'"+ ebook.getTitle()+"\', null );";


        try{
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(newSql);
//            return true;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ebook.getAuthor());
            preparedStatement.setString(2, ebook.getTitle());
            preparedStatement.setDate(3, java.sql.Date.valueOf(ebook.getPublishedDate()));
            preparedStatement.setString(4, ebook.getFormat());

            int rowsInserted = preparedStatement.executeUpdate();

            return (rowsInserted != 1) ? false : true;

        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        String sql = "DELETE FROM ebook WHERE id >= 0;";

        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private EBook getEBookFromResultSet(ResultSet resultSet) throws SQLException{
        return new EBookBuilder()
                .setId(resultSet.getLong("id"))
                .setTitle(resultSet.getString("title"))
                .setAuthor(resultSet.getString("author"))
                .setPublishedDate(new java.sql.Date(resultSet.getDate("publishedDate").getTime()).toLocalDate())
                .setFormat(resultSet.getString("format"))
                .build();
    }
}

