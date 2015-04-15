package com.codex.restCrud.service;

/**
 * @author Natasha Levkovich
 * @since 11.04.15
 */
public class NewProjectForm {
    private String title;

    private String synopsis;


    //private short count;



    /* public short getCount() {
         return count;
     }

     public void setCount(short count) {
         this.count = count;
     }*/
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
