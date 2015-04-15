package com.codex.restCrud;

import java.io.Serializable;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
public class IdResponseUI implements Serializable {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
