package act4_2;

import hibernate_resources.*;
import org.hibernate.*;
import pojos.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Act4_2 {

    private static final Scanner sc = new Scanner(System.in);
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public static void main(String[] args) throws Exception {
        menu();
    }
    
    /**
     * Shows the department with the id that is passed to it
     * @param id
    */
    public static void showDepartment(int id) {
        System.out.println("-----showDepartment-----");

        Session sesion = sessionFactory.openSession();
        Departments department = (Departments) sesion.get(Departments.class, id);
        if (!(department == null)) {
            System.out.println(department);
        } else {
            System.out.println("there is no department with the id " + id);
        }
        sesion.close();
    }
    
    /**
     * Shows the teacher with the id that is passed to it
     * @param id
    */
    public static void showTeacher(int id) {
        System.out.println("-----showTeacher-----");

        Session sesion = sessionFactory.openSession();
        Teachers teacher = (Teachers) sesion.get(Teachers.class, id);
        if (!(teacher == null)) {
            System.out.println(teacher);
        } else {
            System.out.println("there is no teacher with the id " + id);
        }
        sesion.close();
    }
    /**
     * shows all teachers in a department with the id is passed to it
     * @param id
    */
    public static void showTeachersInDepartment(int id) {
        System.out.println("-----showTeachersInDepartment-----");

        Session sesion = sessionFactory.openSession();
        Departments department = (Departments) sesion.get(Departments.class, id);

        if (!(department == null)) {
            System.out.println(department);
            department.getTeacherses().forEach(teacher -> {
                System.out.println(teacher);
            });
        } else {
            System.out.println("there is no department with the id " + id);
        }
        sesion.close();
    }
    /**
     * Create a department.
     * @param deptNum
     * @param name
     * @param office
    */
    public static void createDepartment(int deptNum, String name, String office) {
        System.out.println("-----createDepartment-----");

        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();

        Departments department = new Departments(deptNum,name,office);
        sesion.save(department);
        tx.commit();
        System.out.println("Department Created");
        sesion.close();
    }
    /**
     * Create a new teacher with a new teacher assigned.
     * @param deptNum
     * @param name
     * @param office
     * @param id
     * @param namet
     * @param surnamet
     * @param emailt
     * @param startDatet
     * @param salary
    */
    public static void createTeacherAndDepartment(int deptNum, String name, String office, int id, String namet, String surnamet, String emailt, Date startDatet, Integer salary) {
        System.out.println("-----createTeacherAndDepartment-----");

        Session sesion = sessionFactory.openSession();
        Transaction tx;

        tx = sesion.beginTransaction();
        Departments department = new Departments(deptNum,name,office);
        sesion.save(department);
        tx.commit();
        System.out.println("Department Created");

        tx = sesion.beginTransaction();
        Teachers teacher = new Teachers(id,department,namet,surnamet,emailt,startDatet,salary);
        sesion.save(teacher);
        tx.commit();
        System.out.println("Teacher Created");
        sesion.close();
    }
    /**
     * Create a new teacher in a existing department.
     * @param deptNum
     * @param id
     * @param namet
     * @param surnamet
     * @param emailt
     * @param startDatet
     * @param salary
    */
    public static void createTeacherInExistingDepartment(int deptNum, int id, String namet, String surnamet, String emailt, Date startDatet, Integer salary) {
        System.out.println("-----createTeacherInExistingDepartment-----");

        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();

        Departments department = (Departments) sesion.get(Departments.class, deptNum);

        if (!(department == null)) {
            Teachers teacher = new Teachers(id,department,namet,surnamet,emailt,startDatet,salary);
            sesion.save(teacher);
            tx.commit();
            System.out.println("Teacher Created");
        } else {
            System.out.println("there is no department with the id " + deptNum);
        }
        sesion.close();
    }
    /**
     * Delete a teacher with the id is passed to it
     * @param id
    */
    public static void deleteTeacher(int id) {
        System.out.println("-----deleteTeacher-----");
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();

        Teachers teacher = (Teachers) sesion.get(Teachers.class, id);
        if (!(teacher == null)) {
            sesion.delete(teacher);
            tx.commit();
            System.out.println("Teacher Deleted");
        } else {
            System.out.println("there is no teacher with the id " + id);
        }
        sesion.close();
    }
    /**
     * Delete a department with the id is passed to it
     * @param deptNum
    */
    public static void deleteDepartment(int deptNum) {
        System.out.println("-----deleteDepartment-----");
        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departments department = (Departments) sesion.get(Departments.class, deptNum);

        if (!(department == null)) {
            if (department.getTeacherses().size() > 0) {
                System.out.println(department.getName() + " has associate professors. Do you want to delete them together with the department? (Y/N)");
                String res = sc.nextLine();
                switch (res.toUpperCase()) {
                    case "Y":
                        department.getTeacherses().forEach(teacherObject -> {
                            Teachers teacher = (Teachers) teacherObject;
                            System.out.println("Deleting Teacher '" + teacher.getName() + "'");
                            sesion.delete(teacher);
                        });
                        System.out.println("Deleting Department " + department.getName());
                        sesion.delete(department);
                        tx.commit();
                        break;
                    case "N":
                        System.out.println("No data has been deleted");
                        break;
                    default:
                        deleteDepartment(deptNum);
                        break;
                }
            } else {
                sesion.delete(department);
                tx.commit();
            }
        } else {
            System.out.println("there is no department with the id " + deptNum);
        }
        sesion.close();
    }
    /**
     * Assigns a salary to all teachers in a department
     * @param deptNum
     * @param salary
    */
    public static void setSalaryOfDepartment(int deptNum, int salary) {
        System.out.println("-----setSalaryOfDepartment-----");

        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departments department = (Departments) sesion.get(Departments.class, deptNum);

        if (!(department == null)) {
            if (department.getTeacherses().size() > 0) {
                department.getTeacherses().forEach(teacherObject -> {
                    Teachers teacher = (Teachers) teacherObject;
                    System.out.println("Updating Salary Of Teacher '" + teacher.getName() + "'");
                    teacher.setSalary(salary);
                });
                tx.commit();
            } else {
                System.out.println(department.getName() + " does not have teachers");
            }
        } else {
            System.out.println("there is no department with the id " + deptNum);
        }

    }
    /**
     * Raises a percentage of salary to all professors in a department who are considered seniors
     * @param deptNum
     * @param per
     * @param years
     * @throws java.lang.Exception
    */
    public static void riseSalaryOfDepartmentSeniors(int deptNum, float per, int years) throws Exception {
        System.out.println("-----riseSalaryOfDepartmentSeniors-----");

        Session sesion = sessionFactory.openSession();
        Transaction tx = sesion.beginTransaction();
        Departments department = (Departments) sesion.get(Departments.class, deptNum);

        if (!(department == null)) {
            if (department.getTeacherses().size() > 0) {
                department.getTeacherses().forEach((Object teacherObject) -> {
                    Teachers teacher = (Teachers) teacherObject;
                    
                    Date startDate = teacher.getStartDate();
                    Date today = new Date(System.currentTimeMillis());
                    
                    long date1InMs = startDate.getTime();
                    long date2InMs = today.getTime();

                    long timeDiff = 0;
                    if (date1InMs > date2InMs) {
                        timeDiff = date1InMs - date2InMs;
                    } else {
                        timeDiff = date2InMs - date1InMs;
                    }

                    int yearsWorking = (int) (timeDiff / ((1000 * 60 * 60 * 24)));
                    
                    if (yearsWorking >= (years*365)) {
                        if (!(teacher.getSalary() == null)) {
                            float f = (float) (1.0 + (per / 100.0));
                            float newSalary = teacher.getSalary() * f;
                            int d = Math.round(newSalary);
                            System.out.println(teacher.getName() + " charged " + teacher.getSalary() + ", now charge " + d);
                            teacher.setSalary(d);
                        } else {
                            System.out.println(teacher.getName() + "'s salary is null");
                        }
                    }
                });
                tx.commit();
            } else {
                System.out.println(department.getName() + " does not have teachers");
            }
        } else {
            System.out.println("there is no department with the id " + deptNum);
        }
    }

    public static void menu() throws Exception {
        boolean key = true;

        do{
            System.out.println("\n\n\nChoose an option:\n"
                    + "1. Show a department by ID\n"
                    + "2. Show a teacher by ID\n"
                    + "3. Show the teachers in existing department\n"
                    + "4. Create new department\n"
                    + "5. Create new teacher with new department associated\n"
                    + "6. Create new teacher with existing department associated\n"
                    + "7. Delete a teacher\n"
                    + "8. Delete a department\n"
                    + "9. Set salary of whole department\n"
                    + "10. Rise salary for seniors of department\n"
                    + "11. Quit\n");

            try {
                int op = Integer.parseInt(sc.nextLine());
                if (op > 0 && op < 12) {
                    int id;
                    int deptNum, salary, per, years;
                    String name, office;
                    String namet, surnamet, emailt, d;
                    Date startDatet;

                    switch (op) {
                        case 1:
                            System.out.println("ID (Department): ");
                            id = Integer.parseInt(sc.nextLine());
                            showDepartment(id);
                            break;
                        case 2:
                            System.out.println("ID (Teacher): ");
                            id = Integer.parseInt(sc.nextLine());
                            showTeacher(id);
                            break;
                        case 3:
                            System.out.println("ID (Department): ");
                            id = Integer.parseInt(sc.nextLine());
                            showTeachersInDepartment(id);
                            break;
                        case 4:
                            System.out.println("deptNum (Department): ");
                            deptNum = Integer.parseInt(sc.nextLine());
                            System.out.println("name (Department): ");
                            name = sc.nextLine();
                            System.out.println("office (Department): ");
                            office = sc.nextLine();
                            createDepartment(deptNum, name, office);
                            break;
                        case 5:
                            System.out.println("deptNum (Department): ");
                            deptNum = Integer.parseInt(sc.nextLine());
                            System.out.println("name (Department): ");
                            name = sc.nextLine();
                            System.out.println("office (Department): ");
                            office = sc.nextLine();

                            System.out.println("id (Teacher): ");
                            id = Integer.parseInt(sc.nextLine());
                            System.out.println("name (Teacher): ");
                            namet = sc.nextLine();
                            System.out.println("surname (Teacher): ");
                            surnamet = sc.nextLine();
                            System.out.println("email (Teacher): ");
                            emailt = sc.nextLine();
                            System.out.println("start date (dd/MM/yyyy) (Teacher): ");
                            d = sc.nextLine();
                            startDatet = new SimpleDateFormat("dd/MM/yyyy").parse(d);
                            System.out.println("salary (Teacher): ");
                            salary = Integer.parseInt(sc.nextLine());
                            createTeacherAndDepartment(deptNum, name, office, id, namet, surnamet, emailt, startDatet, salary);
                            break;
                        case 6:
                            System.out.println("deptNum (Department): ");
                            deptNum = Integer.parseInt(sc.nextLine());
                            System.out.println("id (Teacher): ");
                            id = Integer.parseInt(sc.nextLine());
                            System.out.println("name (Teacher): ");
                            namet = sc.nextLine();
                            System.out.println("surname (Teacher): ");
                            surnamet = sc.nextLine();
                            System.out.println("email (Teacher): ");
                            emailt = sc.nextLine();
                            System.out.println("start date (dd/MM/yyyy) (Teacher): ");
                            d = sc.nextLine();
                            startDatet = new SimpleDateFormat("dd/MM/yyyy").parse(d);
                            System.out.println("salary (Teacher): ");
                            salary = Integer.parseInt(sc.nextLine());

                            createTeacherInExistingDepartment(deptNum, id, namet, surnamet, emailt, startDatet, salary);
                            break;
                        case 7:
                            System.out.println("id (Teacher): ");
                            id = Integer.parseInt(sc.nextLine());
                            deleteTeacher(id);
                            break;
                        case 8:
                            System.out.println("deptNum (Department): ");
                            deptNum = Integer.parseInt(sc.nextLine());
                            deleteDepartment(deptNum);
                            break;
                        case 9:
                            System.out.println("deptNum (Department): ");
                            deptNum = Integer.parseInt(sc.nextLine());
                            System.out.println("salary (Of all teachers of the Department " + deptNum + "): ");
                            salary = Integer.parseInt(sc.nextLine());
                            setSalaryOfDepartment(deptNum, salary);
                            break;
                        case 10:
                            System.out.println("deptNum (Department): ");
                            deptNum = Integer.parseInt(sc.nextLine());
                            System.out.println("Percentage to raise salary (Of Seniors teachers of the Department " + deptNum + "): ");
                            float perf = sc.nextFloat();
                            sc.nextLine();
                            System.out.println("Years to be considered Senior: ");
                            years = Integer.parseInt(sc.nextLine());
                            riseSalaryOfDepartmentSeniors(deptNum, perf, years);
                            break;
                        case 11:
                            key = false;
                            sessionFactory.close();
                            break;
                    }
                } else {
                    System.out.println("You have to enter a number from 1 to 11");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect Format");
            }
        }while (key); 
    }
}
