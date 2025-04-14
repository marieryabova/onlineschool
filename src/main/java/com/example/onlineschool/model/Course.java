package com.example.onlineschool.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.io.Serializable;

/**
 * Сущность, представляющая учебный курс в системе.
 
 * Содержит информацию о курсе, включая его характеристики, стоимость и статистику.
 * Реализует {@link Serializable} для поддержки сериализации.
 
 *
 * @see Entity
 * @see Data
 * @see Table
 */
@Entity
@Data
public class Course implements Serializable {

    /**
     * Уникальный идентификатор курса в системе.
     
     * Автоматически генерируется базой данных при создании записи.
     
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Название курса.
     
     * Должно быть уникальным и информативным для студентов.
     
     */
    private String title;

    /**
     * Подробное описание содержания курса.
     
     * Включает темы, навыки и знания, которые получит студент.
     
     */
    private String description;

    /**
     * Стоимость прохождения курса.
     
     * Указывается в базовой валюте системы. Должна быть положительной.
     
     */
    private Integer price;

    /**
     * Категория курса.
     
     * Определяет направление курса (например: "Программирование", "Дизайн", "Маркетинг").
     
     */
    private String category;

    /**
     * Уровень сложности курса.
     */
    private String level;

    /**
     * Продолжительность курса в часах.
     
     * Включает общее время всех занятий и самостоятельной работы.
     
     */
    private Integer duration;

    /**
     * Количество учеников, записанных на курс.
     
     * Вычисляется автоматически с помощью SQL-запроса.
     * Обновляется при изменении состава студентов.
     
     */
    @Formula("(SELECT COUNT(cs.id) FROM course_student cs WHERE cs.course_id = id)")
    private Integer studentsNumber;
}