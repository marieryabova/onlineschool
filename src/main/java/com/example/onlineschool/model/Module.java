package com.example.onlineschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Сущность, представляющая учебный модуль курса.
 
 * Модуль является структурной единицей курса, содержащей логически связанный материал.
 * Каждый модуль принадлежит определенному курсу и содержит последовательность уроков.
 
 *
 * @see Entity
 * @see Data
 * @see Course
 */
@Entity
@Data
public class Module {

    /**
     * Уникальный идентификатор модуля в системе.
     
     * Генерируется автоматически базой данных при создании записи.
     * Используется как первичный ключ таблицы модулей.
     
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Курс, к которому относится данный модуль.
     
     * Связь многие-к-одному с сущностью {@link Course}.
     * Внешний ключ course_id в таблице базы данных.
     * Поле обязательно для заполнения.
     
     */
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /**
     * Название модуля.
     
     * Должно кратко и точно отражать содержание модуля.
     * Максимальная длина - 100 символов.
     
     */
    private String moduleTitle;

    /**
     * Подробное описание содержания модуля.
     
     * Включает перечень тем, изучаемых в модуле, и формируемые компетенции.
     * Может содержать HTML-разметку для форматирования.
     
     */
    private String moduleDescription;
}