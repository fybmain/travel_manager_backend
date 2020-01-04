from db import Database
from mocker import Mocker

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
    Employee = 0
    DepartmentManager = 1
    Manager = 2
    Admin = 3


class ApplicationStatus:
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

    manager_id = mocker.insert_user(1, UserRole.Manager)
    department_id = mocker.insert_department(manager_id, "总办")
    mocker.update_user_departmentId(department_id, manager_id)


if __name__ == '__main__':
    main()