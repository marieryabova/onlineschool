package com.example.onlineschool.service;

import com.example.onlineschool.dto.RegisterRequest;
import com.example.onlineschool.model.Role;
import com.example.onlineschool.model.Student;
import com.example.onlineschool.model.Teacher;
import com.example.onlineschool.model.User;
import com.example.onlineschool.repository.StudentRepository;
import com.example.onlineschool.repository.TeacherRepository;
import com.example.onlineschool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервис для обработки операций аутентификации и регистрации.
 
 * Предоставляет функциональность для:
 * <ul>
 *   <li>Регистрации новых пользователей (студентов, преподавателей, администраторов)</li>
 *   <li>Создания соответствующих профилей в системе</li>
 *   <li>Проверки уникальности учетных данных</li>
 * </ul>
 
 */
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Регистрирует нового пользователя в системе.
     
     * В зависимости от роли создает:
     * <ul>
     *   <li>Для STUDENT - профиль студента</li>
     *   <li>Для TEACHER - профиль преподавателя</li>
     *   <li>Для ADMIN - только учетную запись (специальный логин 'root')</li>
     * </ul>
     
     *
     * @param request DTO с данными для регистрации
     * @throws RuntimeException если:
     *                          <ul>
     *                            <li>Пользователь с таким именем уже существует</li>
     *                            <li>Попытка создать администратора с именем, отличным от 'root'</li>
     *                          </ul>
     */
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (request.getRole() == Role.ADMIN && !request.getUsername().equals("root")) {
            throw new RuntimeException("Только один администратор с именем 'root' разрешен");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        switch (request.getRole()) {
            case STUDENT -> registerStudent(request, user);
            case TEACHER -> registerTeacher(request, user);
        }

        userRepository.save(user);
    }

    /**
     * Создает и сохраняет профиль студента.
     
     * Заполняет все обязательные поля студента из запроса регистрации
     * и связывает созданный профиль с учетной записью пользователя.
     
     *
     * @param request DTO с данными для регистрации
     * @param user    создаваемая учетная запись пользователя
     */
    private void registerStudent(RegisterRequest request, User user) {
        Student student = new Student();
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setSurname(request.getSurname());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());

        Student savedStudent = studentRepository.save(student);
        user.setStudent(savedStudent);
    }

    /**
     * Создает и сохраняет профиль преподавателя.
     
     * Заполняет все обязательные поля преподавателя из запроса регистрации,
     * устанавливает опыт работы (по умолчанию 0, если не указан)
     * и связывает созданный профиль с учетной записью пользователя.
     
     *
     * @param request DTO с данными для регистрации
     * @param user    создаваемая учетная запись пользователя
     */
    private void registerTeacher(RegisterRequest request, User user) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(request.getTeacherFirstName());
        teacher.setLastName(request.getTeacherLastName());
        teacher.setSurname(request.getTeacherSurname());
        teacher.setEmail(request.getTeacherEmail());
        teacher.setPhone(request.getTeacherPhone());
        teacher.setSpecialization(request.getSpecialization());
        teacher.setExperience(request.getExperience() != null ? request.getExperience() : 0);

        Teacher savedTeacher = teacherRepository.save(teacher);
        user.setTeacher(savedTeacher);
    }
}