import java.util.List;
import java.util.Scanner;

public class StudentApp {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== Student Management Menu ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Department: ");
                    String dept = sc.next();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();
                    dao.addStudent(new Student(id, name, dept, marks));
                    break;
                case 2:
                    List<Student> students = dao.getAllStudents();
                    for (Student s : students)
                        System.out.printf("ID: %d | Name: %s | Dept: %s | Marks: %.2f%n",
                                s.getStudentID(), s.getName(), s.getDepartment(), s.getMarks());
                    break;
                case 3:
                    System.out.print("Enter Student ID to update: ");
                    int uid = sc.nextInt();
                    System.out.print("Enter New Name: ");
                    String nname = sc.next();
                    System.out.print("Enter New Department: ");
                    String ndept = sc.next();
                    System.out.print("Enter New Marks: ");
                    double nmarks = sc.nextDouble();
                    dao.updateStudent(new Student(uid, nname, ndept, nmarks));
                    break;
                case 4:
                    System.out.print("Enter Student ID to delete: ");
                    int did = sc.nextInt();
                    dao.deleteStudent(did);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);

        sc.close();
    }
}
