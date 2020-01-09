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

        fake_name = "MOCK" + full_name(last_names, first_names)

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

        department_name = "MOCK" + department_name

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

        food = random.randint(10, 100)
        hotel = random.randint(10, 100)
        vehicle = random.randint(10, 100)
        other = random.randint(10, 100)

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
            '河北': ['石家庄','唐山','秦皇岛','承德'],
            '山东': ['济南','青岛','临沂','淄博'],
            '湖南': ['长沙','衡阳','湘潭','邵阳','岳阳','株洲'],
            '湖北': ['武汉', '襄阳', '黄冈', '宜昌'],
            '江西': ['南昌','九江','上饶','景德镇'],
            '北京': ['北京'],
            '上海': ['上海'],
            '重庆': ['重庆'],
            '广东': ['广州', '深圳'],

        }
        province = list(dict.keys())[(random.randint(0, len(dict.keys())-1))]
        city_list = dict.get(province)
        city = city_list[(random.randint(0, len(city_list)-1))]

        reasons = ["技术现场支持客户",
                   "合作洽谈",
                   "售卖服务",
                   "参加会议"]

        reason = reasons[random.randint(0, len(reasons)-1)]
        reason = "MOCK" + reason

        if self.get_next_id("travel_application") is None:
            nid=1
        else:
            nid = self.get_next_id("travel_application")+1

        time_str = datetime.today().strftime("%Y-%m-%d %H:%M:%S")

        food = random.randint(10, 100)
        hotel = random.randint(10, 100)
        vehicle = random.randint(10, 100)
        other = random.randint(10, 100)

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