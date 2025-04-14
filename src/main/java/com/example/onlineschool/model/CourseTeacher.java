package com.example.onlineschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Сущность, представляющая связь между курсом и преподавателем.
 
 * Содержит информацию о назначении преподавателя на курс и его роли в преподавании.
 * Используется для организации учебного процесса и распределения преподавательской нагрузки.
 
 *
 * @see Entity
 * @see Data
 * @see Course
 * @see Teacher
 */
@Entity
@Data
public class CourseTeacher {

    /**
     * Уникальный идентификатор связи курс-преподаватель.
     
     * Генерируется автоматически базой данных при создании записи.
     
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Курс, на который назначен преподаватель.
     
     * Связь многие-к-одному с сущностью {@link Course}.
     * Внешний ключ course_id в таблице базы данных.
     * Поле обязательно для заполнения.
     
     */
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /**
     * Преподаватель, назначенный на курс.
     
     * Связь многие-к-одному с сущностью {@link Teacher}.
     * Внешний ключ teacher_id в таблице базы данных.
     * Поле обязательно для заполнения.
     
     */
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    /**
     * Роль преподавателя в рамках курса.
     
     * Определяет уровень ответственности и обязанности преподавателя.
     * Например:
     * <ul>
     *   <li>создатель курса</li>
     *   <li>руководитель курса</li>
     *   <li>ассистент</li>
     * </ul>
     
     */
    private String role;
}