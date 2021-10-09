public class testOOPs {
    public static void main(String[] args) {
        abc a = new abc(2);
        System.out.println(a.getTest());
    }
}
class abc{
    final int test;
    abc(int test){
        this.test = test;
    }
    public int getTest(){
        return this.test;
    }
}
