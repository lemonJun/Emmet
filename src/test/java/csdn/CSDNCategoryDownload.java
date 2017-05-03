package csdn;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@SuppressWarnings("serial")
public class CSDNCategoryDownload extends JFrame {
    // 常量
    // http://blog.csdn.net/bushijieinside?viewmode=contents
    static String bokeurl = "http://blog.csdn.net/ITer_ZC/article/category/2719317";
    static String myurl = "";
    static int iiii, tttt, jjjj = 0;
    private static String geturl, imgname = "";
    /* private JPanel contentPane; */
    private JTextField textField, textField2;
    private TextArea textArea, textArea_1;
    static String chooserpath = "E:\\csdn_down";
    static int bolgnum = 0;

    /**
     * 博客导出工具
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CSDNCategoryDownload frame = new CSDNCategoryDownload();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public CSDNCategoryDownload() {
        //        setIconImage(Toolkit.getDefaultToolkit().getImage(CSDNColumnDownload.class.getResource("tupian.png")));

        // setIconImage(Toolkit.getDefaultToolkit().getImage("res//title.png"));

        getContentPane().setBackground(new Color(153, 153, 204));

        setTitle("博客导出工具可导出图片版");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 750, 600);
        final Frame f = new Frame("my frame");// 为了打开文件选择时开个窗口

        JButton button = new JButton("搜索");
        button.setBounds(499, 36, 93, 23);
        button.setFont(new Font("新宋体", Font.PLAIN, 13));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iiii = 0;
                tttt = 0;
                bolgnum = 0;

                try {
                    selectstatistics(textField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                myurl = textField.getText();

                for (int k = 1; k <= (bolgnum / 20 + 1); k++) {
                    if (k == 1) {
                        bolgnums(myurl);
                    } else {
                        bolgnums(myurl + "/" + k);
                    }
                }
            }
        });
        getContentPane().setLayout(null);
        getContentPane().add(button);

        textField = new JTextField();
        textField.setBounds(130, 36, 351, 23);
        textField.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setText(bokeurl);
        getContentPane().add(textField);
        textField.setColumns(10);

        JLabel label = new JLabel("输入博客专栏地址：");
        label.setBounds(10, 36, 110, 23);
        label.setFont(new Font("新宋体", Font.PLAIN, 15));
        getContentPane().add(label);
        JLabel label_1 = new JLabel("博客目录：");
        label_1.setBounds(32, 64, 107, 22);
        label_1.setFont(new Font("新宋体", Font.PLAIN, 15));
        getContentPane().add(label_1);

        textArea = new TextArea("", 5, 40);
        textArea.setBounds(61, 92, 531, 303);
        // TextArea.SCROLLBARS_BOTH
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("新宋体", Font.PLAIN, 15));

        getContentPane().add(textArea);

        JButton btnNewButton_1 = new JButton("导出博客");
        btnNewButton_1.setBounds(598, 105, 114, 58);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iiii = 0;
                tttt = 0;
                bolgnum = 0;
                int newbol = 0;
                try {
                    newbol = selectstatistics(textField.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                myurl = textField.getText();

                for (int k = 1; k <= (newbol / 20 + 1); k++) {
                    if (k == 1) {
                        bolgrun(myurl);
                    } else {
                        bolgrun(myurl + "/" + k);
                    }
                }
            }
        });
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 15));

        getContentPane().add(btnNewButton_1);

        /*
         * 以下为文本输入框
         */
        textField2 = new JTextField();
        textField2.setBounds(58, 401, 362, 30);
        textField2.setFont(new Font("新宋体", Font.PLAIN, 13));
        textField2.setText(chooserpath);
        getContentPane().add(textField2);
        textField2.setColumns(10);

        /*
         * 选择文件夹
         */
        JButton button2 = new JButton("选择目的地文件夹");// 新建按钮选择路径
        button2.setBounds(430, 401, 141, 30);
        button2.setFont(new Font("新宋体", Font.PLAIN, 12));
        button2.addActionListener(new ActionListener() {// 为路径选择button设置监听器
            @Override
            public void actionPerformed(ActionEvent e) {// 监听事件
                JFileChooser chooser = new JFileChooser();// 文件选择器
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 设置文件筛选模版
                int returnVal = chooser.showOpenDialog(f);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    // 设置文件夹名称为选择的文件路径
                    chooserpath = chooser.getSelectedFile().getPath();
                    // 将路径显示在对应的文本框中
                    textField2.setText(chooser.getSelectedFile().getPath());
                }
            }
        });
        getContentPane().add(button2);// 加入容器中

        JButton button_1 = new JButton("清空文本框");
        button_1.setBounds(598, 176, 114, 30);
        button_1.setFont(new Font("新宋体", Font.PLAIN, 12));
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
                textArea_1.setText("");
                System.out.println("qingkong");
            }
        });
        getContentPane().add(button_1);

        JButton button_2 = new JButton("打开文件夹");
        button_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop().open(new File(textField2.getText()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        button_2.setFont(new Font("新宋体", Font.PLAIN, 12));
        button_2.setBounds(605, 401, 107, 30);
        getContentPane().add(button_2);

        JLabel label_2 = new JLabel("博客信息：");
        label_2.setForeground(Color.YELLOW);
        label_2.setBounds(598, 216, 69, 15);
        getContentPane().add(label_2);

        textArea_1 = new TextArea("", 5, 40);
        textArea_1.setForeground(Color.BLACK);
        textArea_1.setFont(new Font("新宋体", Font.PLAIN, 15));
        textArea_1.setBounds(598, 237, 126, 149);
        getContentPane().add(textArea_1);

    }

    // 字节流保存文件

    protected void mywriter(String path, Document doc, String sb) {
        try {
            FileOutputStream output = new FileOutputStream(path);
            BufferedOutputStream bw = new BufferedOutputStream(output);
            //            System.out.println(doc.getElementById("article_content").toString());
            bw.write("<html><head>".getBytes("utf-8"));
            bw.write("  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> ".getBytes("utf-8"));
            bw.write(sb.getBytes("utf-8"));
            bw.write(" </head> <body><h1>".getBytes("utf-8"));
            bw.write(doc.getElementsByClass("article_title").text().toString().getBytes("utf-8"));
            bw.write("</h1>".getBytes("utf-8"));
            bw.write(doc.getElementById("article_content").toString().getBytes("utf-8"));
            bw.write(" </body></html>".getBytes("utf-8"));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* 请求网址，用jsoup对网页进行筛选得到目录的url标签体 */
    public Elements selectUrl(String myurl) throws IOException {
        System.out.println(myurl);
        Document doc;
        doc = Jsoup.connect(myurl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0").post();

        tttt++;
        String title = "===第--- " + tttt + " ---页=== \n";// 获得标题头

        textArea.append(title);
        System.out.println("title ============" + title);
        //        Elements links1 = doc.select("span.link_title");// .Select("a[href]");
        Elements links1 = doc.select("span.link_title");// .Select("a[href]");
        for (Element ele : links1) {
            System.out.println("----" + ele.text());
        }
        //        System.out.println("----" + links1);
        // <span class="link_title"><a
        // href="/bushijieinside/article/details/14521165"> HTTP中的Get和Post分析
        // </a></span>
        return links1;

    }

    /* 返回一个dom */
    public Document getdom(String myurl) throws IOException {
        Document doc;
        doc = Jsoup.connect(myurl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0").timeout(0).post();

        return doc;
    }

    /* 请求网址，用jsoup对网页进行筛选得到博客信息文章个数 */
    public int selectstatistics(String myurl) throws IOException {
        Document doc;
        doc = Jsoup.connect(myurl).header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:25.0) Gecko/20100101 Firefox/25.0").timeout(6 * 1000).post();

        Elements links3 = doc.select("div#papelist");// .Select("a[href]");
        String bolgstatistics = "";
        if (links3 != null && links3.size() > 0) {
            String bolgnums = links3.select("span").get(0).text().toString();
            bolgnums = bolgnums.trim();
            bolgnum = Integer.valueOf(bolgnums.substring(0, bolgnums.split(" ")[0].length() - 1));
            System.out.println("bolgnum--" + bolgnum);
            bolgstatistics = links3.toString().substring(25).replaceAll("[<li></lui></span>]", "");
        } else {
            bolgnum = 15;
            bolgstatistics = "<20";
        }
        textArea_1.setText(bolgstatistics);
        return bolgnum;
    }

    /*
     * 得到URL调用保存方法
     */
    public void bolgrun(String myurl2) {
        try {
            Elements allurl = selectUrl(myurl2); // 调用方法返回需要的节点
            for (Element link : allurl) {
                try {
                    iiii++;
                    // 网站链接不完整的/bushijieinside/article/details/11898583
                    String bokeUrl = link.select("a[href]").attr("href");
                    // 改造为可用链接http://blog.csdn.net/bushijieinside/article/details/14495003
                    String useUrl = "http://blog.csdn.net" + bokeUrl;
                    String code = bokeUrl.substring(bokeUrl.lastIndexOf("/"));

                    // 向网址发送请求得到dom
                    Document doc1 = getdom(useUrl);
                    //创建图片保存位置
                    (new File(textField2.getText() + "\\bolgImg")).mkdirs();
                    // 保存博客中图片，并修改博客html文件中的图片超链接

                    //得到Url进行图片下载
                    downloadImg(doc1);

                    //
                    //                StringBuilder sb = downloadCss(doc1);

                    //将获得的内容写入本地html

                    String title2 = "---" + doc1.title().replace(" - 博客频道 - CSDN.NET", "") + "---";// 获得标题头去除多余的字符串
                    // 创造文件夹避免找不到文件夹地址
                    (new File(textField2.getText())).mkdirs();
                    // 对i进行处理是标题从00开始便于后期做成目录

                    // 通过标题构造本地文件名,本地路径
                    //                String bokepath = textField2.getText() + "\\" + y + "》" + doc1.title().replace(" - 博客频道 - CSDN.NET", "").replaceAll("[\"?\\*]", "") + ".html";
                    String bokepath = textField2.getText() + "\\" + code + "_" + doc1.title().replace(" - 博客频道 - CSDN.NET", "").replaceAll("[\"?\\*]", "") + ".html";
                    String bokepath2 = bokepath.replace("（", "").replace("）", "").replace("(", "").replace(")", "");

                    // 创造html文件
                    File bokefile = new File(bokepath2);
                    // 调用写入本地的方法
                    mywriter(bokepath2, doc1, "");

                    String showtext = iiii + "success:" + title2 + "\n";
                    System.out.println(showtext);
                    // System.out.println(showtext);
                    //在界面中反馈出保存成功
                    textArea.append(showtext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void downloadImg(Document doc1) {
        jjjj = 0;
        //在博客heml中查找img标签
        Elements ems = doc1.select("div#article_details").select("img[src]");

        for (Element img : ems) {
            //            System.out.println(img);
            jjjj++;
            geturl = img.attr("src");
            if (geturl.contains("arrow_triangle _down")) {
                continue;
            }
            //            System.out.print(jjjj);
            //            System.out.println(geturl);
            //UUID.randomUUID().hashCode();

            imgname = textField2.getText() + "\\bolgImg\\pic" + (int) (Math.random() * 1999999999 + 100000) + ".gif";
            //            System.out.println(imgname);
            File imgpath = new File(imgname);
            try {
                getImg(geturl, imgpath);
            } catch (Exception e) {
            }
            if (geturl.trim().startsWith("http")) {
                //改变博客html中博客的地址为本地地址，jsoup中的方法
                img.attr("src", imgname);

                // 转换后回写

            }
        }
    }

    /* 保存网站图片 */
    public static void getImg(String imgurl, File imageFile) throws Exception {

        URL url = new URL(imgurl);
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);
        //new一个文件对象用来保存图片，默认保存当前工程根目录  
        // File imageFile = new File("BeautyGirl.jpg");  
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);
        //写入数据  
        outStream.write(data);
        //关闭输出流  
        outStream.close();
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来  
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);
        }
        //关闭输入流  
        inStream.close();
        //把outStream里的数据写入内存  
        return outStream.toByteArray();

    }

    // 博客数量
    public void bolgnums(String url2) {
        try {
            Elements allurl = selectUrl(url2);

            for (Element link : allurl) {
                iiii++;
                String linkText = link.text();// 网页标题
                String lin = link.select("a[href]").attr("href") + "\n";// 网站链接
                String showtext = iiii + "---" + linkText + lin;
                // System.out.println(i+"==="+lin +"==>"+linkText);

                textArea.append(showtext);

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}