package com.example.onlineschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Сущность, представляющая учебный урок в системе.
 
 * Урок является минимальной структурной единицей учебного материала,
 * содержащей конкретную тему или практическое задание в рамках модуля.
 
 *
 * @see Entity
 * @see Data
 * @see Module
 */
@Entity
@Data
public class Lesson {

    /**
     * Уникальный идентификатор урока в системе.
     
     * Генерируется автоматически базой данных при создании записи.
     * Используется как первичный ключ таблицы уроков.
     
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Модуль, к которому принадлежит данный урок.
     
     * Связь многие-к-одному с сущностью {@link Module}.
     * Внешний ключ module_id в таблице базы данных.
     * Поле обязательно для заполнения.
     
     */
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    /**
     * Название урока.
     
     * Должно кратко и точно отражать содержание урока.
     * Рекомендуемый формат: "Тема: Конкретное название".
     * Максимальная длина - 120 символов.
     
     */
    private String lessonTitle;

    /**
     * Подробное описание урока.
     
     * Содержит:
     * <ul>
     *   <li>Цели и задачи урока</li>
     *   <li>Теоретический материал</li>
     *   <li>Практические задания</li>
     *   <li>Критерии оценки</li>
     * </ul>
     * Может содержать HTML-разметку для форматирования.
     
     */
    private String lessonDescription;
}