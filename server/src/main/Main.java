package main;


import database.Operator;
import serverUDP.Connector;
import serverUDP.UDPServer;

import java.net.SocketException;
import java.util.Objects;


/**
 * Main class - Entrance point
 *
 * @version 1.8.0.301
 * @autor gvd2808
 */
public class Main {
    public static void main(String[] args) throws SocketException {
        System.out.println( "  @@@@@@@@@      @@@@@@@@   ITMO\n" +
                " @@       @@    @@       @  musicBands\n" +
                "@@   @@@@  @@  @@  @      @ server\n" +
                " @@    @@   @@@@   @     @  PoweredBy\n" +
                "  @@@@@@@    @@    @@@@@@   GVD\n");

        Operator operator = new Operator();
        CollectionHolder cHolder = new CollectionHolder(operator);
        cHolder.readMap();

        int port = 30100;
        while (!Connector.available(port)) {
            port++;
        }

        UDPServer udpServer = new UDPServer(port, cHolder, operator);
        Thread serverThread = new Thread(udpServer);
        serverThread.start();

        while (serverThread.isAlive() && !serverThread.isInterrupted()){
            String input = KeyboardReader.input("Print \"stop\" to interrupt the server");
            if(Objects.equals(input, "stop")) {
                serverThread.interrupt();
                break;
            }
        }

        try {
            if (!serverThread.isInterrupted()) serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        udpServer.turnServerOff();
        operator.disconnect();

        //          Доработать программу из лабораторной работы №6 следующим образом:
        //1.	Организовать хранение коллекции в реляционной СУБД (PostgresQL). Убрать хранение коллекции в файле.
        //2.	Для генерации поля id использовать средства базы данных (sequence).
        //3.	Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД
        //4.	Все команды получения данных должны работать с коллекцией в памяти, а не в БД
        //5.	Организовать возможность регистрации и авторизации пользователей. У пользователя есть возможность указать пароль.
        //6.	Пароли при хранении хэшировать алгоритмом SHA-256
        //7.	Запретить выполнение команд не авторизованным пользователям.
        //8.	При хранении объектов сохранять информацию о пользователе, который создал этот объект.
        //9.	Пользователи должны иметь возможность просмотра всех объектов коллекции, но модифицировать могут только принадлежащие им.
        //10.	Для идентификации пользователя отправлять логин и пароль с каждым запросом.
        //          Необходимо реализовать многопоточную обработку запросов.
        //1.	Для многопоточного чтения запросов использовать Cached thread pool
        //2.	Для многопоточной обработки полученного запроса использовать Fixed thread pool
        //3.	Для многопоточной отправки ответа использовать создание нового потока (java.lang.Thread)
        //4.	Для синхронизации доступа к коллекции использовать синхронизацию чтения и записи с помощью java.util.concurrent.locks.ReadWriteLock
    }
}

