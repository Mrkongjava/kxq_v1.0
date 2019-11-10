package com.group.common.utils.ValidateCode;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ValidateCode1 {

    // 验证码
    private Integer code = null;
    // 验证码图片Buffer
    private BufferedImage image = null;

    //生成验证码
    public void createVerifyCode() {
        int width = 80;
        int height = 32;
        //创建内存图像对象
       image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();//获取画笔
        // 设置图片背景颜色和宽高
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // 划定边界和设置边界颜色
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // 创建一个随机实例来生成代码
        Random rdm = new Random();
        // 弄模糊（在图片上生成50个干扰的点）
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // 生成随机码
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));//设置随机码的字体和大小
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        code = calc(verifyCode);//计算随机码运算后的值
    }

    public BufferedImage getBuffImg() {
        return image;
    }

    public Integer getCode() {
        return code;
    }

//    //检查验证码
//    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
//        if(user == null || goodsId <=0) {
//            return false;
//        }
//        //检查用户传入的验证码和缓存中的是否一致
//        Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, Integer.class);
//        if(codeOld == null || codeOld - verifyCode != 0 ) {
//            return false;
//        }
//        //删除缓存中的该验证码
//        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId);
//        return true;
//    }

    /**
     * 计算随机验证码的值，例如“1+3*4” 得到结果就是8，入参是一个含有运算符和数字的字符串，不是图片
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     *  生成含有运算符号 + - * 的验证码字符串
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }
}
