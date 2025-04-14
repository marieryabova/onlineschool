package com.example.onlineschool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

/**
 * Сущность, представляющая связь между курсом и учеником.
 
 * Содержит информацию о:
 * <ul>
 *   <li>Курсе, на который записан студент</li>
 *   <li>Учеником, записанном на курс</li>
 *   <li>Статусе оплаты и прохождения курса</li>
 *   <li>Информации о сертификате</li>
 * </ul>
 *
 * @see Entity
 * @see Data
 * @see Course
 * @see Student
 */
@Entity
@Data
public class CourseStudent {

    /**
     * Уникальный идентификатор связи курс-студент.
     
     * Генерируется автоматически базой данных при создании записи.
     
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Курс, на который записан студент.
     
     * Связь многие-к-одному с сущностью {@link Course}.
     * Внешний ключ course_id в таблице базы данных.
     
     */
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /**
     * Студент, записанный на курс.
     
     * Связь многие-к-одному с сущностью {@link Student}.
     * Внешний ключ student_id в таблице базы данных.
     
     */
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /**
     * Статус оплаты курса учеником.
     
     * Может содержать значения:
     * <ul>
     *   <li>оплачено</li>
     *   <li>оплачено частично</li>
     *   <li>не оплачено</li>
     * </ul>
     
     */
    private String paymentStatus;

    /**
     * Статус прохождения курса учеником.
     
     * Может содержать значения:
     * <ul>
     *   <li>не начат</li>
     *   <li>в процессе</li>
     *   <li>завершен</li>
     *   <li>не пройден</li>
     * </ul>
     
     */
    private String courseCompletionStatus;

    /**
     * Информация о сертификате.
     
     * Содержит данные о выданном сертификате или о его отсутствии
     
     */
    private String certificateInfo;
}