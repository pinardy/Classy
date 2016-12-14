package lucis.classy;

/**
 * Created by Pin on 11-Dec-16.
 */

public class FeedbackResults {
    Integer studentCount = 0;
    Integer count1 = 0;
    Integer count2 = 0;
    Integer count3 = 0;
    Integer count4 = 0;
    Integer count5 = 0;
    String clarify = "";

    public FeedbackResults(){
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public int getStudentCount() {
        return studentCount;
    }
    public int getCount1() {
        return count1;
    }
    public int getCount2() {
        return count2;
    }
    public int getCount3() {
        return count3;
    }
    public int getCount4() {
        return count4;
    }
    public int getCount5() {
        return count5;
    }
    public String getClarify(){
        return clarify;
    }

}
