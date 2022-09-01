package main;


import common.User;
import database.Operator;
import serverUDP.UDPServer;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;


/**
 * Main class - Entrance point
 *
 * @version 1.8.0.301
 * @autor gvd2808
 */
public class Main {

    public static void administrate(CommandExecutor commandExecutor) {
        while (!commandExecutor.getExitStatus()) {
            String input = KeyboardReader.input("\n(Enter command)");
            commandExecutor.runCommand(input);
        }
    }

    public static void main(String[] args) throws SocketException {

        Operator operator = new Operator();
        CollectionHolder cHolder = new CollectionHolder(operator);
        cHolder.readMap();
        UDPServer udpServer = new UDPServer(50000, cHolder, operator);
        udpServer.run();
          //trying to reach the data-file


        //Operator.runStatement("postgres", "leto2003");
        //          Доработать программу из лабораторной работы №6 следующим образом:
        //TODO 1.	Организовать хранение коллекции в реляционной СУБД (PostgresQL). Убрать хранение коллекции в файле.
        //TODO 2.	Для генерации поля id использовать средства базы данных (sequence).
        //TODO 3.	Обновлять состояние коллекции в памяти только при успешном добавлении объекта в БД
        //TODO 4.	Все команды получения данных должны работать с коллекцией в памяти, а не в БД
        //TODO 5.	Организовать возможность регистрации и авторизации пользователей. У пользователя есть возможность указать пароль.
        //TODO 6.	Пароли при хранении хэшировать алгоритмом SHA-256
        //TODO 7.	Запретить выполнение команд не авторизованным пользователям.
        //TODO 8.	При хранении объектов сохранять информацию о пользователе, который создал этот объект.
        //TODO 9.	Пользователи должны иметь возможность просмотра всех объектов коллекции, но модифицировать могут только принадлежащие им.
        //TODO 10.	Для идентификации пользователя отправлять логин и пароль с каждым запросом.
        //          Необходимо реализовать многопоточную обработку запросов.
        //TODO 1.	Для многопоточного чтения запросов использовать Cached thread pool
        //TODO 2.	Для многопоточной обработки полученного запроса использовать Fixed thread pool
        //TODO 3.	Для многопоточной отправки ответа использовать создание нового потока (java.lang.Thread)
        //TODO 4.	Для синхронизации доступа к коллекции использовать синхронизацию чтения и записи с помощью java.util.concurrent.locks.ReadWriteLock
        operator.disconnect();

    }

//        MAIN:  while (true) {
//                String input = /*KeyboardReader.input("\n\n\nturn off - 0\nadmin mode-1\nserver mode - 2")*/ "2";
//                switch (input.trim()) {
//                    case "0":
//                        break MAIN;
//                    case "1":
//                        administrate(commandExecutor);
//                        continue;
//                    case "2": {
//                        Integer port = null;
//                        try {
//                            String portInput = /*KeyboardReader.input("Set the server port: ")*/ "50000";
//                            if (portInput == null) continue;
//                            port = new Integer(portInput);
//                        } catch (RuntimeException e) {
//                            System.out.println("wrong port");
//                        }
//                        UDPServer server = null;
//                        try {
//                            server = new UDPServer(port, cHolder);
//                        } catch (SocketException e) {
//                            System.out.println("port "+ port + " is occupied");
//                            continue;
//                        }
//                        server.launchServer();
//                        server.run();
//                        server.turnServerOff();
//                        System.out.println("it's ok");
//                        //commandExecutor.runCommand("save");
//                    }
//                }
//            }
//        }
}

