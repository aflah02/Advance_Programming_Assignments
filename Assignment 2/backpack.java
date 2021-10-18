import java.util.*;

public class backpack {
    private final ArrayList<instructor> instructorTracker;
    private final ArrayList<student> studentTracker;
    private final ArrayList<assessments> assessmentTracker;
    private final ArrayList<LectureMaterial> lectureMaterialTracker;
    private final ArrayList<commentContainer> commentTracker;
    Scanner sc;
    Scanner sc2;
    backpack(){
        this.commentTracker = new ArrayList<>();
        this.instructorTracker = new ArrayList<>();
        this.studentTracker = new ArrayList<>();
        this.assessmentTracker = new ArrayList<>();
        this.lectureMaterialTracker = new ArrayList<>();
        this.sc = new Scanner(System.in);
        this.sc2 = new Scanner(System.in);
    }
    public static void main(String[] args) {
        backpack backpack = new backpack();
        backpack.instructorTracker.add(new instructor("I0"));
        backpack.instructorTracker.add(new instructor("I1"));
        backpack.studentTracker.add(new student("S0"));
        backpack.studentTracker.add(new student("S1"));
        backpack.studentTracker.add(new student("S2"));
        backpack.printStartMenu();
        int option = backpack.sc.nextInt();
        while (option != 3) {
            if (option == 2) {
                backpack.showStudents();
                System.out.print("Choose id: ");
                int student_number = backpack.sc.nextInt();
                student std = backpack.studentTracker.get(student_number);
                std.login();
                backpack.printStudentMenu(std);
                int menu_option = backpack.sc.nextInt();
                while (menu_option != 7){
                    if (menu_option == 1){
                        backpack.viewLectureMaterials(std);
                    }
                    else if (menu_option == 2){
                        boolean anyOpen = backpack.viewAssessments(std);
                        if (!anyOpen){
                            System.out.println("No Open Assessments");
                        }
                    }
                    else if (menu_option == 3){
                        System.out.println("Pending assessments");
                        boolean isPending = backpack.viewPendingAssessments(std);
                        if (isPending){
                            System.out.print("Enter ID of assessment: ");
                            int assessmentID = backpack.sc.nextInt();
                            assessments assessment = backpack.assessmentTracker.get(assessmentID);
                            if (assessment instanceof quiz){
                                System.out.print(backpack.assessmentTracker.get(assessmentID).getQuestion());
                                String ans = backpack.sc2.nextLine();
                                submissionContainer toSubmit = new submissionContainer(assessment);
                                toSubmit.setAnsGiven(std, ans);
                                backpack.submitAssessment(std, assessment, toSubmit);
                            }
                            else{
                                System.out.print("Enter filename of assignment: ");
                                String fileName = backpack.sc2.nextLine();
                                submissionContainer toSubmit = new submissionContainer(assessment);
                                if (fileName.length() < 4){
                                    System.out.println("Invalid File Name");
                                }
                                else if (fileName.startsWith(".zip", fileName.length()-4)){
                                    toSubmit.setSubmissionName(fileName);
                                    backpack.submitAssessment(std, assessment, toSubmit);
                                }
                                else{
                                    System.out.println("Invalid File Extension");
                                }
                            }
                        }
                    }
                    else if (menu_option == 4){
                        backpack.viewGrades(std);
                    }
                    else if (menu_option == 5){
                        backpack.viewComments(std);
                    }
                    else if (menu_option == 6){
                        System.out.print("Enter Comment: ");
                        String commentText = backpack.sc2.nextLine();
                        Date currentTime = new java.util.Date(System.currentTimeMillis());
                        backpack.addComment(std, currentTime, commentText);
                    }
                    backpack.printStudentMenu(std);
                    menu_option = backpack.sc.nextInt();
                    if (menu_option == 7){
                        std.logout();
                    }
                }
            } else {
                backpack.showInstructors();
                System.out.print("Choose id: ");
                int instructor_number = backpack.sc.nextInt();
                instructor inst = backpack.instructorTracker.get(instructor_number);
                inst.login();
                backpack.printInstructorMenu(inst);
                int menu_option = backpack.sc.nextInt();
                while (menu_option != 9){
                    if (menu_option == 1) {
                        System.out.println("1. Add Lecture Slide\n" +
                                "2. Add Lecture Video");
                        int material_type = backpack.sc.nextInt();
                        if (material_type == 1) {
                            System.out.print("Enter topic of slides: ");
                            String slideTopic = backpack.sc2.nextLine();
                            System.out.print("Enter number of slides: ");
                            int slideCount = backpack.sc.nextInt();
                            System.out.println("Enter content of slides");
                            ArrayList<String> slideContent = new ArrayList<>();
                            for (int i = 0; i < slideCount; i++) {
                                System.out.print("Content of slide " + (i + 1) + ":");
                                slideContent.add(backpack.sc2.nextLine());
                            }
                            Date currentTime = new java.util.Date(System.currentTimeMillis());
                            LectureSlides slides  = new LectureSlides(slideTopic, slideContent, currentTime, inst);
                            backpack.addLectureMaterials(inst, slides);
                        } else if (material_type == 2) {
                            System.out.print("Enter topic of video: ");
                            String videoTopic = backpack.sc2.nextLine();
                            System.out.print("Enter filename of video: ");
                            String fileName = backpack.sc2.nextLine();
                            Date currentTime = new java.util.Date(System.currentTimeMillis());
                            LectureRecordings video = new LectureRecordings(videoTopic, fileName, currentTime, inst);
                            int fileNameLength = fileName.length();
                            if (fileNameLength < 4){
                                System.out.println("Invalid file name");
                            }
                            else if (fileName.startsWith(".mp4", fileNameLength-4)){
                                backpack.addLectureMaterials(inst, video);
                            }
                            else{
                                System.out.println("Invalid File Format, Please only Upload Files with .mp4 Extension");
                            }
                        }
                    } else if (menu_option == 2) {
                        System.out.println("1. Add Assignment\n" +
                                "2. Add Quiz");
                        int assessmentType = backpack.sc.nextInt();
                        if (assessmentType == 1) {
                            System.out.print("Enter problem statement: ");
                            String problemStatement = backpack.sc2.nextLine();
                            System.out.print("Enter max marks: ");
                            int maxMarks = backpack.sc.nextInt();
                            assignment toAddAssignment = new assignment(problemStatement, maxMarks);
                            backpack.addAssessments(inst, toAddAssignment);
                        } else if (assessmentType == 2) {
                            System.out.print("Enter quiz question: ");
                            String quizQuestion = backpack.sc2.nextLine();
                            quiz toAddQuiz = new quiz(quizQuestion);
                            backpack.addAssessments(inst, toAddQuiz);
                        }
                    }
                    else if (menu_option == 3) {
                        backpack.viewLectureMaterials(inst);
                    }
                    else if (menu_option == 4){
                        boolean anyOpen = backpack.viewAssessments(inst);
                        if (!anyOpen){
                            System.out.println("No Open Assessments");
                        }
                    }
                    else if (menu_option == 5){
                        System.out.println("List of assessments");
                        boolean anyOpen = backpack.viewAssessments(inst);
                        if (!anyOpen){
                            System.out.println("No Open Assessments");
                        }
                        else{
                            System.out.print("Enter ID of assessment to view submissions: ");
                            int assessment_id = backpack.sc.nextInt();
                            assessments assessment = backpack.assessmentTracker.get(assessment_id);
                            System.out.println("Choose ID from these ungraded submissions");
                            boolean anyValid = false;
                            for (int i = 0; i < backpack.studentTracker.size(); i++){
                                if (!backpack.studentTracker.get(i).getStudentSubmissionMap().isEmpty() &&
                                        backpack.studentTracker.get(i).getStudentSubmissionMap().containsKey(assessment) &&
                                        !backpack.studentTracker.get(i).getChosenStudentSubmission(assessment).isGraded()){
                                    System.out.println(i + ". " + backpack.studentTracker.get(i).getName());
                                    anyValid = true;
                                }
                            }
                            if (!anyValid){
                                continue;
                            }
                            int student_no = backpack.sc.nextInt();
                            submissionContainer submission = backpack.studentTracker.get(student_no).getChosenStudentSubmission(assessment);
                            if (assessment instanceof assignment){
                                System.out.println("Submission: " + submission.getSubmissionName());
                            }
                            else{
                                System.out.println("Submission: " + submission.getAssessment().getQuestion() + " , Answer Given: " + submission.getAnsGiven());
                            }
                            System.out.println("-------------------------------");
                            System.out.println("Max Marks: " + submission.getAssessment().getMaxMarks());
                            System.out.print("Marks scored: ");
                            int marksScored = backpack.sc.nextInt();
                            backpack.gradeSubmission(inst, submission, marksScored);
                        }
                    }
                    else if (menu_option == 6){
                        System.out.println("List of Open Assessments: ");
                        boolean anyOpen = backpack.viewAssessments(inst);
                        if (!anyOpen){
                            System.out.println("No Open Assessments");
                        }
                        else{
                            System.out.print("Enter id of Assessment to close: ");
                            int assignment_id = backpack.sc.nextInt();
                            backpack.closeAssessment(inst, assignment_id);
                        }
                    }
                    else if (menu_option == 7){
                        backpack.viewComments(inst);
                    }
                    else if (menu_option == 8){
                        System.out.print("Enter Comment: ");
                        String commentText = backpack.sc2.nextLine();
                        Date currentTime = new java.util.Date(System.currentTimeMillis());
                        backpack.commentTracker.add(new commentContainer(inst, currentTime, commentText));
                    }
                    backpack.printInstructorMenu(inst);
                    menu_option = backpack.sc.nextInt();
                    if (menu_option == 9){
                        inst.logout();
                    }
                }
            }
            backpack.printStartMenu();
            option = backpack.sc.nextInt();
        }
        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    private void viewGrades(student std){
        std.viewGrades(assessmentTracker);
    }

    private boolean viewPendingAssessments(student std) {
        return std.viewPendingAssessments(assessmentTracker);
    }

    private void gradeSubmission(instructor inst, submissionContainer submission, int marksScored) {
        inst.gradeAssessments(submission, marksScored);
    }

    private void submitAssessment(student std, assessments assessment, submissionContainer submission){
        std.submitAssessments(assessment, submission);
    }
    private void addComment(user user, Date currentTime, String commentText) {
        user.addComments(currentTime, commentText, commentTracker);
    }

    private void closeAssessment(instructor inst, int assessmentNumber){
        inst.closeAssessment(assessmentNumber, assessmentTracker);
    }

    private void viewComments(user user){
        user.viewComments(commentTracker);
    }
    private void addAssessments(instructor inst, assessments assessment){
        inst.addAssessments(assessment, assessmentTracker);
    }
    private void viewLectureMaterials(user user){
        user.viewLectureMaterials(lectureMaterialTracker);
    }
    private boolean viewAssessments(user user){
        return user.viewAssessments(assessmentTracker);
    }
    private void addLectureMaterials(instructor inst, LectureMaterial material){
        inst.addLectureMaterial(material, lectureMaterialTracker);
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
    public String getQuestion();
    public int getMaxMarks();
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
        System.out.println("ID: " + index + " Assignment: " + this.getQuestion());
    }

    @Override
    public String getQuestion() {
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
        System.out.println("ID: " + index + " Assignment: " + this.getQuestion() + " Max Marks: " + this.getMaxMarks());
    }

    @Override
    public String getQuestion() {
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
    public String getTitle();
    public Date getUploadDate();
    public instructor getUploader();
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

    @Override
    public String getTitle() { return title; }

    public ArrayList<String> getSlideContent() { return slideContent; }

    @Override
    public Date getUploadDate() { return uploadDate; }

    @Override
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

    @Override
    public String getTitle() { return title; }

    public String getFileName() { return fileName; }

    @Override
    public Date getUploadDate() { return uploadDate; }

    @Override
    public instructor getUploader() { return uploader; }
}
interface user{
    public void login();
    public void logout();
    public void viewComments(ArrayList<commentContainer> commentTracker);
    public void addComments(Date currentTime, String commentText, ArrayList<commentContainer> commentTracker);
    public void viewLectureMaterials(ArrayList<LectureMaterial> lectureMaterials);
    public boolean viewAssessments(ArrayList<assessments> assessmentTracker);
    public String getName();
}

class instructor implements user{
    private final String name;
    private boolean isLoggedin;
    instructor(String name){
        this.isLoggedin = false;
        this.name = name;
    }
    @Override
    public void login() { this.isLoggedin = true; }
    @Override
    public void logout() { this.isLoggedin = false; }
    @Override
    public void viewComments(ArrayList<commentContainer> commentTracker) {
        for (commentContainer comment: commentTracker){
            System.out.println(comment.getComment() + " - " + comment.getUser().getName());
            System.out.println();
        }
    }
    @Override
    public void addComments(Date currentTime, String commentText, ArrayList<commentContainer> commentTracker) { commentTracker.add(new commentContainer(this, currentTime, commentText)); }
    @Override
    public void viewLectureMaterials(ArrayList<LectureMaterial> lectureMaterials) {
        for (LectureMaterial material: lectureMaterials){ material.view();
            System.out.println();}
    }
    @Override
    public boolean viewAssessments(ArrayList<assessments> assessmentTracker) {
        boolean anyOpen = false;
        for (int i = 0; i < assessmentTracker.size(); i++){
            if (!assessmentTracker.get(i).isClose()){
                assessmentTracker.get(i).view(i);
                anyOpen = true;
            }
        }
        return anyOpen;
    }

    public void addLectureMaterial(LectureMaterial lectureMaterial, ArrayList<LectureMaterial> lectureMaterialTracker){
        lectureMaterialTracker.add(lectureMaterial);
    }
    public void addAssessments(assessments assessment, ArrayList<assessments> assessmentTracker){
        assessmentTracker.add(assessment);
    }

    public void gradeAssessments(submissionContainer submission, int marksEarned){
        submission.setMarksEarned(marksEarned);
        submission.setGradingInstructor(this);
        submission.grade();
    }
    public String getName() { return name; }
    public void closeAssessment(int assessmentNumber, ArrayList<assessments> assessmentTracker) {
        assessmentTracker.get(assessmentNumber).close();
    }
}

class student implements user{
    private final HashMap<assessments, submissionContainer> studentSubmission;
    private final String name;
    private boolean isLoggedin;
    student(String name){
        this.studentSubmission = new HashMap<>();
        this.isLoggedin = false;
        this.name = name;
    }
    @Override
    public void login() { this.isLoggedin = true; }
    @Override
    public void logout() { this.isLoggedin = false; }
    @Override
    public void viewComments(ArrayList<commentContainer> commentTracker) {
        for (commentContainer comment: commentTracker){
            System.out.println(comment.getComment() + " - " + comment.getUser().getName());
            System.out.println(comment.getUploadDate());
        }
    }
    @Override
    public void addComments(Date currentTime, String commentText, ArrayList<commentContainer> commentTracker) {
        commentTracker.add(new commentContainer(this, currentTime, commentText));
    }
    @Override
    public void viewLectureMaterials(ArrayList<LectureMaterial> lectureMaterials) {
        for (LectureMaterial material: lectureMaterials){
            material.view();
            System.out.println();
        }
    }
    @Override
    public boolean viewAssessments(ArrayList<assessments> assessmentTracker) {
        boolean anyOpen = false;
        for (int i = 0; i < assessmentTracker.size(); i++){
            if (!assessmentTracker.get(i).isClose()){
                assessmentTracker.get(i).view(i);
                anyOpen = true;
            }
        }
        return anyOpen;
    }
    public void submitAssessments(assessments assessment, submissionContainer submission){
        this.studentSubmission.put(assessment, submission);
    }
    public void viewGrades(ArrayList<assessments> assessmentTracker){
        System.out.println("Graded submissions");
        viewGradedAssessments(assessmentTracker);
        System.out.println();
        System.out.println("Ungraded submissions");
        viewUngradedAssessments(assessmentTracker);
        System.out.println("-----------------");
    }

    public String getName() {
        return name;
    }

    public HashMap<assessments, submissionContainer> getStudentSubmissionMap() {
        return studentSubmission;
    }
    public submissionContainer getChosenStudentSubmission(assessments assessment){
        return studentSubmission.get(assessment);
    }

    public boolean viewPendingAssessments(ArrayList<assessments> assessmentTracker) {
        boolean isPending = false;
        for (int i = 0; i < assessmentTracker.size(); i++){
            if (!studentSubmission.containsKey(assessmentTracker.get(i)) && !assessmentTracker.get(i).isClose()){
                assessmentTracker.get(i).view(i);
                isPending = true;
            }
        }
        if (!isPending){
            System.out.println("No pending assignments");
            return false;
        }
        else {
            return true;
        }
    }

    public void viewGradedAssessments(ArrayList<assessments> assessmentTracker) {
        for (assessments assessments : assessmentTracker) {
            if (studentSubmission.containsKey(assessments) && studentSubmission.get(assessments).isGraded()) {
                if (assessments instanceof assignment) {
                    System.out.println("Submission: " + studentSubmission.get(assessments).getSubmissionName());
                    System.out.println("Marks scored: " + studentSubmission.get(assessments).getMarksEarned());
                    System.out.println("Graded by: " + studentSubmission.get(assessments).getGradingInstructor().getName());
                } else {
                    System.out.println("Question: " + assessments.getQuestion());
                    System.out.println("Ans Given: " + studentSubmission.get(assessments).getAnsGiven());
                    System.out.println("Marks scored: " + studentSubmission.get(assessments).getMarksEarned());
                    System.out.println("Graded by: " + studentSubmission.get(assessments).getGradingInstructor().getName());
                }
            }
        }
    }

    public void viewUngradedAssessments(ArrayList<assessments> assessmentTracker) {
        for (assessments assessments : assessmentTracker) {
            if (studentSubmission.containsKey(assessments) && !studentSubmission.get(assessments).isGraded()) {
                if (assessments instanceof assignment) {
                    System.out.println("Submission: " + studentSubmission.get(assessments).getSubmissionName());
                } else {
                    System.out.println("Question: " + assessments.getQuestion());
                    System.out.println("Ans Given: " + studentSubmission.get(assessments).getAnsGiven());
                }
            }
        }
    }
}

class commentContainer {
    private final user user;
    private final Date uploadDate;
    private final String comment;
    commentContainer(user user, Date uploadDate, String comment){
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

class submissionContainer{
    private final assessments assessment;
    private instructor gradingInstructor;
    private String ansGiven;
    private String submissionName;
    private int marksEarned;
    private boolean isGraded;
    public submissionContainer(assessments assessment)
    {
        this.assessment = assessment;
        this.isGraded = false;
    }

    public void grade(){
        this.isGraded = true;
    }
    public void setGradingInstructor(instructor gradingInstructor) {
        this.gradingInstructor = gradingInstructor;
    }
    public void setMarksEarned(int marksEarned) {
        this.marksEarned = marksEarned;
    }

    public assessments getAssessment() {
        return assessment;
    }

    public instructor getGradingInstructor() {
        return gradingInstructor;
    }

    public String getAnsGiven() {
        return ansGiven;
    }

    public int getMarksEarned() {
        return marksEarned;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public String getSubmissionName() {
        return submissionName;
    }

    public void setSubmissionName(String submissionName) {
        this.submissionName = submissionName;
    }

    public void setAnsGiven(student std, String ansGiven) {
        this.ansGiven = ansGiven;
    }
}