package com.my.model.dao.constants;

public class HallConstants {

    private HallConstants() {
    }

    public static final String ADD_HALL =
            "INSERT INTO hall (name, description) VALUES (?, ?)";
    public static final String FIND_HALL_BY_ID =
            "SELECT * FROM hall WHERE id = ?";
    public static final String FIND_ALL_HALLS =
            "SELECT * FROM hall";
    public static final String UPDATE_HALL =
            "UPDATE hall SET name = ? , description = ? WHERE id = ?";
    public static final String DELETE_HALL_BY_ID =
            "DELETE FROM hall  WHERE id = ?";
    public static final String FIND_HALL_BY_NAME =
            "SELECT * FROM hall WHERE name = ?";
    public static final String GET_HALLS =
            "SELECT * " +
                    "FROM hall h INNER JOIN exhibitions_halls eh ON h.id = eh.hall_id " +
                    "INNER JOIN exhibition e on eh.exhibition_id = e.id " +
                    "WHERE e.id = ?";
}
