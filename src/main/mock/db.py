import mysql.connector
from mysql.connector import errorcode

import sys


class Database:

    def __init__(self, username, password, host, port, dbname):
        self.username = username
        self.password = password
        self.host = host
        self.port = port
        self.dbname = dbname

    def init_conn(self):
        try:
            conn = mysql.connector.connect(user=self.username, password=self.password,host=self.host,port=self.port,database=self.dbname)
            return conn
        except mysql.connector.Error as err:
            if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
                print("Something is wrong with your user name or password")
            elif err.errno == errorcode.ER_BAD_DB_ERROR:
                print("Database does not exist")
            else:
                print(err)
            # 数据库初始化失败则退出
            sys.exit(-1)

    @staticmethod
    def close_conn(conn):
        conn.close()

