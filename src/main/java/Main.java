
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static final String TASK = "TASK";

    private static final String COMMAND_REMOVE = "$DEL";
    private static final String COMMAND_SHOWALL = "$SHOW";

    private static JedisPool jedisPool;
    private static Jedis jedis;
    private static Map<String, String> taskList;

    private static Properties properties;

    public static void main(String[] args) {

        readPropertiesFile();

        if (connectRedis()) {
            showIntro();
            showAllTodoList(false);
        } else {
            System.out.println("Please Start Redis Server . . .");
        }


    }

    private static void readPropertiesFile() {
        InputStream input = null;
        properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            input = loader.getSystemResourceAsStream("template.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void showAllTodoList(boolean isEnd) {
        getTaskList();
        if (isEmptyTaskList()) {
            System.out.println(properties.getProperty("app.notask"));
            System.out.println(properties.getProperty("app.endline"));
        } else {
            System.out.println(properties.getProperty("app.header"));
            System.out.println(properties.getProperty("app.endline"));
            int i = 1;
            for (Map.Entry<String, String> entry : taskList.entrySet()) {
                System.out.format("%-20s \t\t\t\t\t\t\t\t\t\t\t%-10s\n", (i + ") " + entry.getValue()), ("#tid = " + entry.getKey()));
                i++;
            }
            System.out.println(properties.getProperty("app.endline"));
        }
        if (!isEnd) {
            showPrompt();
        }
    }

    private static void showIntro() {
        System.out.println(properties.getProperty("app.banner"));
        System.out.println(properties.getProperty("app.endline"));
        System.out.println(properties.getProperty("app.helper"));
        System.out.println(properties.getProperty("app.endline"));

    }

    private static boolean isEmptyTaskList() {
        return taskList.isEmpty() ? true : false;
    }

    private static void showPrompt() {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter Task or COMMAND :> ");
        String task = kb.nextLine().trim();
        if (COMMAND_SHOWALL.equalsIgnoreCase(task)) {
            showAllTodoList(false);
            showPrompt();
        } else if (task.toUpperCase().startsWith(COMMAND_REMOVE)) {
            String[] strArr = task.split("\\s");
            try {
                removeTodo(strArr[1]);
                showPrompt();
            } catch (Exception e) {
                System.out.println("$DEL has no param");
                showPrompt();
            }
        } else if (!("".equals(task))) {
            saveTodo(taskList.size() + 1, task);
            showPrompt();
        } else {
            showAllTodoList(true);
        }
    }

    private static void removeTodo(String key) {
        Long isSucess = jedis.hdel(TASK, key);
        if (isSucess.longValue() != 0) {
            System.out.println("Delete Complete");
            getTaskList();
        } else {
            System.out.println("Delete Not Complete");
        }

    }

    private static void saveTodo(Integer i, String task) {
        Long isSucess = jedis.hset(TASK, i.toString(), task);
        if (isSucess.longValue() != 0) {
            System.out.println("Save Complete");
            getTaskList();
        } else {
            System.out.println("Save Not Complete");
        }

    }

    private static Map<String, String> getTaskList() {
        return taskList = jedis.hgetAll(TASK);
    }

    private static boolean connectRedis() {
        jedisPool = new JedisPool(properties.getProperty("redis.host"), Integer.parseInt(properties.getProperty("redis.port")));
        try {
            jedis = jedisPool.getResource();
            return true;
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }

    }


}
