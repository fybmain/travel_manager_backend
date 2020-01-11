"""
mocker
"""

import random
import string
from datetime import datetime

from name_generator import full_name, last_names, first_names


def random_string(stringLength=10):
    """Generate a random string of fixed length """
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(stringLength))


class Mocker:

    def __init__(self, conn):
        self.conn = conn

    def get_next_id(self, table_name):
        cursor = self.conn.cursor()
        a = cursor.execute("select max(id) from " + table_name)
        ids = cursor.fetchone()
        return ids[0]

    def insert_user(self, department_id, role):
        if self.get_next_id("user") is None:
            nid=1
        else:
            nid = self.get_next_id("user")+1

        fake_workid = str(random.randint(100000000, 999999999))
        fake_email = fake_workid + "@" + "qq.com"

        fake_name = full_name(last_names, first_names)

        fake_hash = random_string(50)
        sql = ("INSERT INTO user (id, department_id, email, name, password_hash, role, status, telephone, work_id) " \
               "VALUES ({}, {}, '{}', '{}', '{}', {}, {}, '{}', '{}')".format(nid, department_id, fake_email, fake_name, fake_hash, role, 1, fake_workid, fake_workid))

        print("SQL:", sql)

        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

        return nid

    def update_user_department_id(self, department_id, user_id):
        sql = ("UPDATE user SET department_id = {} WHERE id = {}".format(department_id, user_id))
        print("SQL:", sql)
        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

    def insert_department(self, manager_id, department_name):
        if self.get_next_id("department") is None:
            nid=1
        else:
            nid = self.get_next_id("department")+1


        sql = "INSERT INTO department (id, manager_id, name) VALUES ({}, {}, '{}')".format(nid, manager_id, department_name)
        print("SQL:", sql)
        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

        return nid

    def update_department_manager(self, manager_id, department_id):
        sql = ("UPDATE department SET manager_id = {} WHERE department_id = {}".format(manager_id, department_id))
        print("SQL:", sql)
        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

    # NO USE
    def insert_picture(self, url):
        if self.get_next_id("picture") is None:
            nid=1
        else:
            nid = self.get_next_id("picture")+1
        time = "2016-01-01"

        sql = "INSERT INTO picture (id, upload_time, url) VALUES ({}, '{}', '{}')".format(nid, time, url)
        print("SQL:", sql)
        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

        return nid

    def insert_payment(self, applicant_id, department_id, pics, status, travel_id):
        if self.get_next_id("payment_application") is None:
            nid=1
        else:
            nid = self.get_next_id("payment_application")+1

        month = random.randint(1, 12)
        monthStr = ""
        if month < 10:
            monthStr = "0" + str(month)
        else:
            monthStr = str(month)

        time_str = datetime.today().strftime("2019-" + monthStr + "-%d %H:%M:%S")

        food = random.randint(800, 2000)
        hotel = random.randint(800, 2000)
        vehicle = random.randint(800, 2000)
        other = random.randint(800, 2000)

        sql = "INSERT INTO payment_application (id, applicant_id, apply_time, department_id, food_payment, hotel_payment, invoiceurls, other_payment, status, travel_id, vehicle_payment) " \
              "VALUES ({}, {}, '{}', {}, {}, {}, '{}', {}, {}, {}, {})"\
              .format(nid, applicant_id, time_str, department_id, food, hotel, pics, other, status, travel_id, vehicle)
        print("SQL:", sql)

        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

        return nid

    def insert_travel(self, applicant_id, department_id, status, paid):
        dict= {
            '河北省': ['石家庄市','唐山市','秦皇岛市','承德市'],
            '山东省': ['济南市','青岛市','临沂市','淄博市'],
            '湖南省': ['长沙市','衡阳市','湘潭市','邵阳市','岳阳市','株洲市'],
            '湖北省': ['武汉市', '襄阳市', '黄冈市', '宜昌市'],
            '江西省': ['南昌市','九江市','上饶市','景德镇市'],
            '北京市': ['北京市'],
            '上海市': ['上海市'],
            '重庆省': ['重庆市'],
            '广东省': ['广州市', '深圳市'],
        }
        province = list(dict.keys())[(random.randint(0, len(dict.keys())-1))]
        city_list = dict.get(province)
        city = city_list[(random.randint(0, len(city_list)-1))]

        reasons = ["技术现场支持客户",
                   "合作洽谈",
                   "售卖服务",
                   "参加会议"]

        reason = reasons[random.randint(0, len(reasons)-1)]

        if self.get_next_id("travel_application") is None:
            nid=1
        else:
            nid = self.get_next_id("travel_application")+1

        time_str = datetime.today().strftime("%Y-%m-%d %H:%M:%S")

        food = random.randint(100, 600)
        hotel = random.randint(100, 600)
        vehicle = random.randint(100, 600)
        other = random.randint(100, 600)

        month = random.randint(1, 12)
        monthStr = ""
        if month < 10:
            monthStr = "0" + str(month)
        else:
            monthStr = str(month)

        start_time = "2019-" + monthStr + "-01 01:00:00"
        end_time = datetime.today().strftime("2019-" + monthStr + "-%d 00:00:00")

        # apply time
        time_now = datetime.today().strftime("2019-" + monthStr + "-%d 00:00:00")


        sql = "INSERT INTO travel_application (id, applicant_id, apply_time, city, department_id, end_time, food_budget, hotel_budget, other_budget, paid, province, reason, start_time, status, vehicle_budget) " \
              "VALUES ({}, {}, '{}', '{}', {}, '{}', {}, {}, {}, {}, '{}', '{}', '{}', {}, {})"\
              .format(nid, applicant_id, time_now, city, department_id, end_time, food, hotel, other, paid, province, reason, start_time, status, vehicle)

        print("SQL:", sql)

        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()

        return nid

    def insert_msg(self, msg, uid):
        if self.get_next_id("message") is None:
            nid=1
        else:
            nid = self.get_next_id("message")+1

        sql = "INSERT INTO message (id, message, receiver_id) VALUES ({}, '{}', {})".format(nid, msg, uid)

        print("SQL:", sql)

        cursor = self.conn.cursor()
        cursor.execute(sql)
        self.conn.commit()
