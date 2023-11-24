package com.example.myapplication;

/**
 * Класс заметок
 */
public class Note {
    /**
     * Тема заметки
     */

    public String theme;
    /**
     * Текст заметки
     */
    public String noteText;

    public Note(String theme, String noteText) {
        this.theme = theme;
        this.noteText = noteText;
    }

}
