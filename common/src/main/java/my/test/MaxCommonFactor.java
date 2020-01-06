package my.test;

import java.math.BigDecimal;

/**
 * Created by eilir on 2019/12/17.
 */
public class MaxCommonFactor {
    public static long getResult(long a, long b, boolean first){
        long f,s,shu;
        if(first) {
            f = a > b ? a : b;
            s = a > b ? b : a;
        }else{
            f = a;
            s = b;
        }
        long x = f % s;
        shu = f/s;
        System.out.println(f + " = " + s + " * " + shu + " + " + x);
        if(first && x == 0){
            return s;
        }else if( s % x == 0){
            shu = s/x;
            System.out.println(s + " = " + x + " * " + shu + " + " + 0);
            return x;
        }else{
            return getResult(s, x, false);
        }
    }
    //带渐进分数式
    public static long getResult3(long a, long b, boolean first, StringBuffer sb, StringBuffer tmp){
        long f,s,shu;
        if(first) {
            f = a > b ? a : b;
            s = a > b ? b : a;
        }else{
            f = a;
            s = b;
        }
        long x = f % s;
        shu = f/s;
        System.out.println(f + " = " + s + " * " + shu + " + " + x);
        sb = sb.append(first ? shu : " + 1/(" + shu);
        tmp = tmp.append(")");
        System.out.println("渐进分数：" + sb );
        if(first && x == 0){
            return s;
        }else if( s % x == 0){
            shu = s/x;
            System.out.println(s + " = " + x + " * " + shu + " + " + 0);
            //渐进分数
            sb.deleteCharAt(sb.lastIndexOf("("));
            tmp = tmp.deleteCharAt(tmp.length() - 1);
            sb = sb.append(tmp);
            System.out.println("渐进分数终：" + sb);
            return x;
        }else{
            return getResult3(s, x, false, sb, tmp);
        }
    }
    public static int getResult2(int a, int b){
        int f = a > b ? a : b;
        int s = a > b ? b : a;
        int x = f % s;
        if(x == 0){
            return s;
        }else if( s % x == 0){
            return x;
        }else{
            return getResult2(s, x);
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
//        System.out.println(MaxCommonFactor.getResult(42897L, 18644L, true));
        long a = 42897L, b = 18644L, res;
        System.out.println(res = MaxCommonFactor.getResult3(a, b, true, new StringBuffer(), new StringBuffer()));
//        DecimalFormat df1 = new DecimalFormat("0.000000");
        System.out.println(a + "/" + res + " ÷ " + b + "/" + res + " = " + a / res + " ÷ " + b / res + " = " + new BigDecimal(a / res).divide(new BigDecimal(b / res), 10, BigDecimal.ROUND_HALF_UP));//.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
        System.out.println(System.currentTimeMillis() - start);
//        start = System.currentTimeMillis();
//        System.out.println(MaxCommonFactor.getResult2(164, 368));
//        System.out.println(System.currentTimeMillis() - start);
    }

}
