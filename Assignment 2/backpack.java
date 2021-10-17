import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class backpack {
    private final ArrayList<instructor> instructorTracker;
    private final ArrayList<student> studentTracker;
    private final ArrayList<assessments> assessmentTracker;
    private final ArrayList<LectureMaterial> lectureMaterialTracker;
    private final ArrayList<comments> commentTracker;
    Scanner sc;
    backpack(){
        this.commentTracker = new ArrayList<>();
        this.instructorTracker = new ArrayList<>();
        this.studentTracker = new ArrayList<>();
        this.assessmentTracker = new ArrayList<>();
        this.lectureMaterialTracker = new ArrayList<>();
        this.sc = new Scanner(System.in);
        instructorTracker.add(new instructor("I0"));
        instructorTracker.add(new instructor("I1"));
        studentTracker.add(new student("S0"));
        studentTracker.add(new student("S1"));
        studentTracker.add(new student("S2"));
    }
    public static void main(String[] args) {
        backpack backpack = new backpack();
        backpack.printStartMenu();
        int option = backpack.sc.nextInt();
        while (option != 3) {
            if (option == 2) {
                backpack.showStudents();
                System.out.print("Choose id: ");
                int student_number = backpack.sc.nextInt();
                student std = backpack.studentTracker.get(student_number);
                backpack.printStudentMenu(std);
                int menu_option = backpack.sc.nextInt();
                while (menu_option != 7){
                    if (menu_option == 1){
                        backpack.viewLectureMaterials(std);
                    }
                    else if (menu_option == 2){
                        backpack.viewAssessments(std);
                    }
//                    else if (menu_option == 3){
//                        //TODO
//                    }
//                    else if (menu_option == 4){
//                        //TODO
//                    }
                    else if (menu_option == 5){
                        backpack.viewComments(std);
                    }
                    else if (menu_option == 6){
                        System.out.print("Enter Comment: ");
                        String commentText = backpack.sc.nextLine();
                        Date currentTime = new java.util.Date(System.currentTimeMillis());
                        backpack.addComment(std, currentTime, commentText);
                    }
                    backpack.printStudentMenu(std);
                    menu_option = backpack.sc.nextInt();
                }
            } else {
                backpack.showInstructors();
                System.out.print("Choose id: ");
                int instructor_number = backpack.sc.nextInt();
                instructor inst = backpack.instructorTracker.get(instructor_number);
                backpack.printInstructorMenu(inst);
                int menu_option = backpack.sc.nextInt();
                while (menu_option != 9){
                    if (menu_option == 1) {
                        System.out.println("1. Add Lecture Slide\n" +
                                "2. Add Lecture Video");
                        int material_type = backpack.sc.nextInt();
                        backpack.sc.nextLine();
                        if (material_type == 1) {
                            System.out.print("Enter topic of slides: ");
                            String slideTopic = backpack.sc.nextLine();
                            System.out.print("Enter number of slides: ");
                            int slideCount = backpack.sc.nextInt();
                            backpack.sc.nextLine();
                            System.out.println("Enter content of slides");
                            ArrayList<String> slideContent = new ArrayList<>();
                            for (int i = 0; i < slideCount; i++) {
                                System.out.print("Content of slide " + (i + 1) + ":");
                                slideContent.add(backpack.sc.nextLine());
                            }
                            Date currentTime = new java.util.Date(System.currentTimeMillis());
                            backpack.addLectureSlides(slideTopic, slideContent, currentTime, inst);
                        } else if (material_type == 2) {
                            System.out.print("Enter topic of video: ");
                            String videoTopic = backpack.sc.nextLine();
                            System.out.print("Enter filename of video: ");
                            String fileName = backpack.sc.nextLine();
                            Date currentTime = new java.util.Date(System.currentTimeMillis());
                            backpack.addLectureRecording(videoTopic, fileName, currentTime, inst);
                        }
                    } else if (menu_option == 2) {
                        System.out.println("1. Add Assignment\n" +
                                "2. Add Quiz");
                        int assessmentType = backpack.sc.nextInt();
                        backpack.sc.nextLine();
                        if (assessmentType == 1) {
                            System.out.print("Enter problem statement: ");
                            String problemStatement = backpack.sc.nextLine();
                            System.out.print("Enter max marks: ");
                            int maxMarks = backpack.sc.nextInt();
                            backpack.addAssignment(inst, problemStatement, maxMarks);
                        } else if (assessmentType == 2) {
                            System.out.print("Enter quiz question: ");
                            String quizQuestion = backpack.sc.nextLine();
                            backpack.addQuiz(inst, quizQuestion);
                        }
                    }
                    else if (menu_option == 3) {
                        backpack.viewLectureMaterials(inst);
                    }
                    else if (menu_option == 4){
                        backpack.viewAssessments(inst);
                    }
//                    else if (menu_option == 5){
//                        // TODO
//                    }
                    else if (menu_option == 6){
                        System.out.println("List of Open Assignments:");
                        backpack.viewAssessments(inst);
                        System.out.print("Enter id of assignment to close: ");
                        int assignment_id = backpack.sc.nextInt();
                        backpack.closeAssessment(inst, assignment_id);
                    }
                    else if (menu_option == 7){
                        backpack.viewComments(inst);
                    }
                    else if (menu_option == 8){
                        System.out.print("Enter Comment: ");
                        String commentText = backpack.sc.nextLine();
                        Date currentTime = new java.util.Date(System.currentTimeMillis());
                        backpack.commentTracker.add(new comments(inst, currentTime, commentText));
                    }
                    backpack.printInstructorMenu(inst);
                    menu_option = backpack.sc.nextInt();
                }
            }
            backpack.printStartMenu();
            option = backpack.sc.nextInt();
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    private void addComment(user user, Date currentTime, String commentText) {
        user.addComments(currentTime, commentText, commentTracker);
    }

    private void closeAssessment(instructor inst, int assessmentNumber){
        inst.closeAssessment(assessmentNumber, assessmentTracker);
    }
    private void addQuiz(instructor inst, String quizQuestion) {
        inst.addQuiz(quizQuestion, assessmentTracker);
    }

    private void addAssignment(instructor instructor, String problemStatement, int maxMarks){
        instructor.addAssignment(problemStatement, maxMarks, assessmentTracker);
    }
    private void viewComments(user user){
        user.viewComments(commentTracker);

    }
    private void viewLectureMaterials(user user){
        user.viewLectureMaterials(lectureMaterialTracker);
    }
    private void viewAssessments(user user){
        user.viewAssessments(assessmentTracker);
    }
    private void addLectureSlides(String slideTopic, ArrayList<String> slideContent, Date currentTime, instructor inst){
        inst.addSlides(slideTopic, slideContent, currentTime, lectureMaterialTracker);
    }
    private void addLectureRecording(String videoTopic, String fileName, Date currentTime, instructor inst){
        int fileNameLength = fileName.length();
        if (fileNameLength < 4){
            System.out.println("Invalid file name");
        }
        else if (fileName.startsWith(".mp4", fileNameLength-4)){
            inst.addVideos(videoTopic, fileName, currentTime, lectureMaterialTracker);
        }
        else{
            System.out.println("Invalid File Format, Please only Upload Files with .mp4 Extension");
        }
    }
    private void showInstructors(){
        System.out.println("Instructors");
        for (int i = 0; i < instructorTracker.size(); i++){
            System.out.println(i + " - " + instructorTracker.get(i).getName());
        }
    }
    private void showStudents(){
        System.out.println("Students");
        for (int i = 0; i < studentTracker.size(); i++){
            System.out.println(i + " - " + studentTracker.get(i).getName());
        }
    }
    private void printStartMenu(){
        System.out.println("""
                Welcome to Backpack
                1. Enter as instructor
                2. Enter as student
                3. Exit""");
    }
    private void printInstructorMenu(instructor inst){
        System.out.println("Welcome " + inst.getName());
        System.out.println("""
                INSTRUCTOR MENU
                1. Add class material
                2. Add assessments
                3. View lecture materials
                4. View assessments
                5. Grade assessments
                6. Close assessment
                7. View comments
                8. Add comments
                9. Logout
                """);
    }
    private void printStudentMenu(student std){
        System.out.println("Welcome " + std.getName());
        System.out.println("""
                STUDENT MENU
                1. View lecture materials
                2. View assessments
                3. Submit assessment
                4. View grades
                5. View comments
                6. Add comments
                7. Logout""");
    }
}
interface assessments{
    public void addQuestion(String s);
    public void setMaxMarks(int maxMarks);
    public void view(int index);
    public void close();
    public boolean isClose();
}

class quiz implements assessments{
    private String quizQuestion;
    private int maxMarks;
    private boolean close;
    quiz(String quizQuestion){
        addQuestion(quizQuestion);
        setMaxMarks(1);
        close = false;
    }
    @Override
    public void addQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    @Override
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public void view(int index) {
        System.out.println("ID: " + index + " Assignment: " + this.getQuizQuestion());
    }

    public String getQuizQuestion() {
        return quizQuestion;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    @Override
    public void close() {
        this.close = true;
    }

    @Override
    public boolean isClose() {
        return this.close;
    }
}

class assignment implements assessments{
    private String problemStatement;
    private int maxMarks;
    private boolean close;
    assignment(String problemStatement, int maxMarks){
        addQuestion(problemStatement);
        setMaxMarks(maxMarks);
        close = false;
    }
    @Override
    public void addQuestion(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    @Override
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public void view(int index) {
        System.out.println("ID: " + index + " Assignment: " + this.getProblemStatement() + " Max Marks: " + this.getMaxMarks());
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public int getMaxMarks() {
        return maxMarks;
    }

    @Override
    public void close() {
        this.close = true;
    }

    @Override
    public boolean isClose() {
        return this.close;
    }
}

interface LectureMaterial{
    public void setTitle(String title);
    public void setTime(Date currentTime);
    public void setInstructor(instructor uploader);
    public void view();
}

class LectureSlides implements LectureMaterial{
    private String title;
    private ArrayList<String> slideContent;
    private Date uploadDate;
    private instructor uploader;

    LectureSlides(String title, ArrayList<String> slideContent, Date uploadDate, instructor uploader){
        setTitle(title);
        setContents(slideContent);
        setTime(uploadDate);
        setInstructor(uploader);
    }

    @Override
    public void setTitle(String title) { this.title = title; }

    public void setContents(ArrayList<String> content) { this.slideContent = content; }

    @Override
    public void setTime(Date currentTime) { this.uploadDate = currentTime; }

    @Override
    public void setInstructor(instructor uploader) { this.uploader = uploader; }

    @Override
    public void view() {
        System.out.println("Title: " + this.getTitle());
        for (int i = 0; i < this.getSlideContent().size(); i++){
            System.out.println("Slide " + (i+1) + ": " + this.getSlideContent().get(i));
        }
        System.out.println("Date of upload: " + this.getUploadDate());
        System.out.println("Uploaded by: " + this.getUploader().getName());
    }

    public String getTitle() { return title; }

    public ArrayList<String> getSlideContent() { return slideContent; }

    public Date getUploadDate() { return uploadDate; }

    public instructor getUploader() { return uploader; }
}

class LectureRecordings implements LectureMaterial{

    private String title;
    private String fileName;
    private Date uploadDate;
    private instructor uploader;

    public LectureRecordings(String videoTopic, String fileName, Date uploadDate, instructor uploader) {
        setTitle(videoTopic);
        setFileName(fileName);
        setTime(uploadDate);
        setInstructor(uploader);
    }

    @Override
    public void setTitle(String title) { this.title = title; }

    public void setFileName(String fileName) { this.fileName = fileName; }

    @Override
    public void setTime(Date currentTime) { this.uploadDate = currentTime; }

    @Override
    public void setInstructor(instructor uploader) { this.uploader = uploader; }

    @Override
    public void view() {
        System.out.println("Title of video: " + this.getTitle());
        System.out.println("Video file: " + this.getFileName());
        System.out.println("Date of upload: " + this.getUploadDate());
        System.out.println("Uploaded by: " + this.getUploader().getName());
    }

    public String getTitle() { return title; }

    public String getFileName() { return fileName; }

    public Date getUploadDate() { return uploadDate; }

    public instructor getUploader() { return uploader; }
}
interface user{
    public void login();
    public void logout();
    public void viewComments(ArrayList<comments> commentTracker);
    public void addComments(Date currentTime, String commentText, ArrayList<comments> commentTracker);
    public void viewLectureMaterials(ArrayList<LectureMaterial> lectureMaterials);
    public void viewAssessments(ArrayList<assessments> assessmentTracker);
    public String getName();
}

class instructor implements user{
    private final String name;
    private boolean isLoggedin;
    instructor(String name){
        this.isLoggedin = false;
        this.name = name;
    }

    public void addQuiz(String quizQuestion, ArrayList<assessments> assessmentTracker) {
        assessmentTracker.add(new quiz(quizQuestion));
    }

    @Override
    public void login() { this.isLoggedin = true; }
    @Override
    public void logout() { this.isLoggedin = false; }
    @Override
    public void viewComments(ArrayList<comments> commentTracker) {
        for (comments comment: commentTracker){
            System.out.println(comment.getComment() + " - " + comment.getUser().getName());
            System.out.println();
        }
    }
    @Override
    public void addComments(Date currentTime, String commentText, ArrayList<comments> commentTracker) { commentTracker.add(new comments(this, currentTime, commentText)); }
    @Override
    public void viewLectureMaterials(ArrayList<LectureMaterial> lectureMaterials) {
        for (LectureMaterial material: lectureMaterials){ material.view(); }
    }
    @Override
    public void viewAssessments(ArrayList<assessments> assessmentTracker) {
        for (int i = 0; i < assessmentTracker.size(); i++){
            if (!assessmentTracker.get(i).isClose()){
                assessmentTracker.get(i).view(i);
            }
        }
    }
    public void addAssignment(String problemStatement, int maxMarks, ArrayList<assessments> assessmentsArrayList){
        assessmentsArrayList.add(new assignment(problemStatement, maxMarks));
    }
    public void addSlides(String slideTopic, ArrayList<String> slideContent, Date currentTime, ArrayList<LectureMaterial> lectureMaterialTracker){
        lectureMaterialTracker.add(new LectureSlides(slideTopic, slideContent, currentTime, this));
    }
    public void addVideos(String videoTopic, String fileName, Date currentTime, ArrayList<LectureMaterial> lectureMaterialTracker){
        lectureMaterialTracker.add(new LectureRecordings(videoTopic, fileName, currentTime, this));
    }
    public void gradeAssessments(){

    }
    public void closeAssessments(){

    }
    public String getName() { return name; }
    public void closeAssessment(int assessmentNumber, ArrayList<assessments> assessmentTracker) {
        assessmentTracker.get(assessmentNumber).close();
    }
}

class student implements user{
    private final String name;
    private boolean isLoggedin;
    student(String name){
        this.isLoggedin = false;
        this.name = name;
    }
    @Override
    public void login() { this.isLoggedin = true; }
    @Override
    public void logout() { this.isLoggedin = false; }
    @Override
    public void viewComments(ArrayList<comments> commentTracker) {
        for (comments comment: commentTracker){
            System.out.println(comment.getComment() + " - " + comment.getUser().getName());
            System.out.println(comment.getUploadDate());
        }
    }
    @Override
    public void addComments(Date currentTime, String commentText, ArrayList<comments> commentTracker) {
        commentTracker.add(new comments(this, currentTime, commentText));
    }
    @Override
    public void viewLectureMaterials(ArrayList<LectureMaterial> lectureMaterials) {
        for (LectureMaterial material: lectureMaterials){
            material.view();
        }
    }
    @Override
    public void viewAssessments(ArrayList<assessments> assessmentTracker) {
        for (int i = 0; i < assessmentTracker.size(); i++){
            assessmentTracker.get(i).view(i);
        }
    }
    public void submitAssessments(){

    }
    public void viewGrades(){

    }
    public String getName() {
        return name;
    }
}

class comments{
    private final user user;
    private final Date uploadDate;
    private final String comment;
    comments(user user, Date uploadDate, String comment){
        this.comment =comment;
        this.uploadDate = uploadDate;
        this.user = user;
    }

    public user getUser() {
        return user;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getComment() {
        return comment;
    }
}