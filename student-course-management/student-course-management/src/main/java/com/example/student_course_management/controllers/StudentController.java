package com.example.student_course_management.controllers;

import com.example.student_course_management.entities.Enrollment;
import com.example.student_course_management.entities.Student;
import com.example.student_course_management.entities.Course;
import com.example.student_course_management.repositories.StudentRepository;
import com.example.student_course_management.repositories.CourseRepository;
import com.example.student_course_management.repositories.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Afficher la liste des étudiants
    @GetMapping
    public String listStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "student/list"; // Chemin vers le template Thymeleaf
    }

    // Afficher le formulaire pour modifier les détails d'un étudiant
    @GetMapping("/edit")
    public String showEditStudentForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("student", getStudentById(id));
        return "student/edit"; // Chemin vers le template Thymeleaf
    }

    // Gérer la soumission du formulaire de modification
    @PostMapping("/edit")
    public String updateStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentRepository.save(student); // Enregistrer les détails modifiés de l'étudiant
        addFlashMessage(redirectAttributes, "successMessage", "L'étudiant a été mis à jour avec succès !");
        return "redirect:/students"; // Rediriger vers la liste des étudiants
    }

    // Afficher le formulaire pour ajouter un nouvel étudiant
    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/add"; // Chemin vers le template Thymeleaf
    }

    // Gérer la soumission du formulaire pour ajouter un nouvel étudiant
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        studentRepository.save(student);
        addFlashMessage(redirectAttributes, "successMessage", "L'étudiant a été ajouté avec succès !");
        return "redirect:/students"; // Rediriger vers la liste des étudiants
    }

    // Afficher les détails d'un étudiant ainsi que ses cours inscrits
    @GetMapping("/detail")
    public String studentDetails(@RequestParam("id") Long id, Model model) {
        Student student = getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("courses", courseRepository.findAll()); // Récupérer tous les cours disponibles
        return "student/detail"; // Chemin vers le template Thymeleaf
    }

    // Gérer l'inscription d'un étudiant à un cours
    @PostMapping("/enroll")
    public String enrollStudentInCourse(@RequestParam("studentId") Long studentId,
                                        @RequestParam("courseId") Long courseId,
                                        RedirectAttributes redirectAttributes) {
        Student student = getStudentById(studentId);
        Course course = getCourseById(courseId);

        // Vérifier si l'étudiant est déjà inscrit dans le cours
        if (isStudentAlreadyEnrolled(studentId, courseId)) {
            addFlashMessage(redirectAttributes, "errorMessage", "Vous êtes déjà inscrit à ce cours.");
            return "redirect:/students/detail?id=" + studentId; // Rediriger avec erreur
        }

        // Créer et enregistrer une nouvelle inscription
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment); // Enregistrer l'inscription directement

        // Définir le message de succès
        addFlashMessage(redirectAttributes, "successMessage", "Inscription réussie dans le cours : " + course.getNom() + " !");
        return "redirect:/students/detail?id=" + studentId; // Rediriger vers la page de détail de l'étudiant
    }

    // Gérer la désinscription d'un étudiant d'un cours
    @PostMapping("/denroll")
    public String denrollStudentFromCourse(@RequestParam("enrollmentId") Long enrollmentId, RedirectAttributes redirectAttributes) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("ID d'inscription invalide : " + enrollmentId));

        // Supprimer l'inscription du référentiel
        enrollmentRepository.delete(enrollment);
        addFlashMessage(redirectAttributes, "successMessage", "Désinscription réussie du cours !");

        return "redirect:/students/detail?id=" + enrollment.getStudent().getId(); // Rediriger vers la page de détail de l'étudiant
    }

    // Gérer la suppression d'un étudiant
    @PostMapping("/delete")
    public String removeStudent(@RequestParam("studentId") Long studentId, @RequestParam("courseId") Long courseId, RedirectAttributes redirectAttributes) {
        if (removeStudentFromCourse(studentId, courseId)) {
            addFlashMessage(redirectAttributes, "successMessage", "L'étudiant a été supprimé avec succès !");
        } else {
            addFlashMessage(redirectAttributes, "errorMessage", "Erreur lors de la suppression de l'étudiant. Veuillez réessayer.");
        }

        return "redirect:/courses/detail?id=" + courseId; // Rediriger vers la page des détails du cours
    }

    // Logique pour retirer l'étudiant du cours
    private boolean removeStudentFromCourse(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (enrollment != null) {
            enrollmentRepository.delete(enrollment);
            return true; // Retourner vrai si réussi
        }
        return false; // Retourner faux si non trouvé
    }

    // Méthode d'assistance pour obtenir un étudiant par ID ou lancer une exception
    private Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID d'étudiant invalide : " + id));
    }

    // Méthode d'assistance pour obtenir un cours par ID ou lancer une exception
    private Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de cours invalide : " + id));
    }

    // Vérifier si un étudiant est déjà inscrit à un cours
    private boolean isStudentAlreadyEnrolled(Long studentId, Long courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    // Méthode utilitaire pour ajouter des messages flash
    private void addFlashMessage(RedirectAttributes redirectAttributes, String key, String message) {
        redirectAttributes.addFlashAttribute(key, message);
    }
}
