package com.my.model.dao.constants;

public class ExhibitionConstants {

    private ExhibitionConstants() {
    }

    public static final String ADD_EXHIBITION =
            "INSERT INTO exhibition (theme, description, start_date, end_date, start_time, end_time, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_EXHIBITION_BY_ID =
            "SELECT * FROM exhibition WHERE id = ?";
    public static final String FIND_ALL_EXHIBITIONS =
            "SELECT * FROM exhibition";
    public static final String UPDATE_EXHIBITION =
            "UPDATE exhibition SET theme = ? , description = ?, start_date = ?, end_date = ?, start_time = ?, end_time = ?, price = ? WHERE id = ?";
    public static final String DELETE_EXHIBITION_BY_ID =
            "DELETE FROM exhibition WHERE id = ?";
    public static final String FIND_EXHIBITION_BY_THEME =
            "SELECT * FROM exhibition WHERE theme = ?";
    public static final String SET_HALLS =
            "INSERT INTO exhibitions_halls VALUES(?, ?)";
    public static final String GET_BY_DEFAULT =
            "SELECT * FROM exhibition LIMIT ?, 5";
    public static final String GET_BY_THEME =
            "SELECT * FROM exhibition ORDER BY theme LIMIT ?, 5;";
    public static final String GET_BY_PRICE =
            "SELECT * FROM exhibition ORDER BY price LIMIT ?, 5;";
    public static final String GET_BY_DATE =
            "SELECT * FROM exhibition WHERE ? BETWEEN start_date AND end_date LIMIT ?, 5;";
    public static final String GET_NUMBER_BY_DATE =
            "SELECT COUNT(*) as 'number' FROM exhibition WHERE ? BETWEEN start_date AND end_date;";
    public static final String GET_NUMBER =
            "SELECT COUNT(*) as 'number' FROM exhibition ";
    public static final String GET_HALLS =
              "SELECT * " +
              "FROM hall h INNER JOIN exhibitions_halls eh ON h.id = eh.hall_id " +
              "INNER JOIN exhibition e on eh.exhibition_id = e.id " +
              "WHERE e.id = ?";
    public static final String GET_STATS =
            "SELECT id, theme, description, start_date, end_date, start_time, end_time, price FROM exhibition;";
    public static final String GET_NUMBER_OF_TICKETS =
            "SELECT COUNT(*) as number FROM orders WHERE exhibition_id = ?";
    public static final String GET_DETAILED_STATS =
            "SELECT COUNT(*) as number_of_bought_tickets FROM orders WHERE exhibition_id = ? and user_id = ?;";

    public static final String FIELD_NUMBER = "number";
    public static final String FIELD_NUMBER_OF_BOUGHT_TICKETS = "number_of_bought_tickets";


}
