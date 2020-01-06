from db import Database
from mocker import Mocker
import random

"""
1. insert admin
2. insert manager(USER)   manager_office(DEPARTMENT)  change manager's departmentId to manager_officeId
3. insert 5 department. insert 5 user(department manager).  change  department's manager to those user's id
4.  for every department:
        insert 30-50 user
        for u in users:
            insert 3-5 travelApplication whith status canbe 1 2 or 4;
            insert 3-10 travelApplication, （status is approved）, and 70% paid is true.   （if paid is true）add them id into a idlist.
            for i in idlist:
                insert a payment application. status 70% is approved. 10% is unapproved  10%is 总经理审批  10% is 部门经理审批.
                每一项报销为random(40, 90)   时间分散到2019年12个月中
"""


class UserRole:
    def __init__(self):
        pass

    Employee = 0
    DepartmentManager = 1
    Manager = 2
    Admin = 3


class ApplicationStatus:
    def __init__(self):
        pass

    NeedDepartmentManagerApprove = 1
    NeedManagerApprove = 2
    ApplicationApproved = 3
    ApplicationNotApproved = 4


def main():
    db = Database("travel", "997icu", "127.0.0.1", "3306", "travelmanager")
    conn = db.init_conn()
    mocker = Mocker(conn)

    # 管理员
    mocker.insert_user(0, UserRole.Admin)

    # 总经理 & 总经理办公室
    manager_id = mocker.insert_user(1, UserRole.Manager)
    department_id = mocker.insert_department(manager_id, "总办")
    mocker.update_user_department_id(department_id, manager_id)

    # 5个部门
    departs = ["云计算事业部", "物联网事业部", "互动娱乐事业部", "网络媒体事业部", "广告事业部"]
    for d in departs:
        depart_manager_id = mocker.insert_user(0, UserRole.DepartmentManager)
        department_id = mocker.insert_department(depart_manager_id, d)
        mocker.update_user_department_id(department_id, manager_id)

        user_cnt = random.randint(30, 50)
        for i in range(user_cnt):
            uid = mocker.insert_user(department_id, UserRole.Employee)

            # 3-5 travelApplication whith status canbe 1 2 or 4
            travel_other_cnt = random.randint(3, 5)
            for j in range(travel_other_cnt):
                status_list = [ApplicationStatus.NeedManagerApprove, ApplicationStatus.NeedDepartmentManagerApprove, ApplicationStatus.ApplicationNotApproved]
                status = status_list[random.randint(0, len(status_list)-1)]
                mocker.insert_travel(uid, department_id, status, False)

            # 3-10 travelApplication, （status is approved）, and 70% paid is true.
            travel_ok_cnt = random.randint(3, 10)
            # status is approved and paid is true
            can_pay_travel_id_list = []
            for k in range(travel_ok_cnt):
                paid_rand = random.randint(30, 90)
                paid = True
                if paid_rand >= 60:
                    paid = False
                tid = mocker.insert_travel(uid, department_id, ApplicationStatus.ApplicationApproved, paid)
                if paid:
                    can_pay_travel_id_list.append(tid)

            """
            # 为这些travel创建payment
            for tid in can_pay_travel_id_list:
                pics = "https://picturesbed.oss-cn-hangzhou.aliyuncs.com/travelmanager/0520201254220450945697959433.png"
                status_rand = random.randint(1, 100)
                status = ApplicationStatus.ApplicationApproved
                # status 70% is approved. 10% is unapproved  10%is 总经理审批  10% is 部门经理审批
                if 70 < status_rand <= 80:
                    status = ApplicationStatus.ApplicationNotApproved
                elif 80 < status_rand <= 90:
                    status = ApplicationStatus.NeedManagerApprove
                elif 90 < status_rand <= 100:
                    status = ApplicationStatus.NeedDepartmentManagerApprove

                mocker.insert_payment(uid, department_id, pics, status, tid)

            """

if __name__ == '__main__':
    main()