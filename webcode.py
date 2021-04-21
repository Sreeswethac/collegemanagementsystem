import os
from flask import *
import pymysql
# from werkzeug.utils import_secure_filename
from werkzeug.utils import secure_filename

obj=Flask(__name__)

con=pymysql.connect(host="localhost",port=3306,user="root",password="",db="collegemanagementsystem")
cmd=con.cursor()
# path="..\static\image"
obj.secret_key='abc'


@obj.route('/contact')
def contact():
    return render_template('contact us.html')



@obj.route('/about')
def about():
    return render_template('aboutus.html')




@obj.route('/')
def main():
    return render_template('forms.html')

@obj.route('/login',methods=['post'])
def login():
    uname=request.form['textfield']
    pwd = request.form['textfield2']
    cmd.execute("select * from login where Username='"+uname+"' and password='"+pwd+"'")
    s=cmd.fetchone()
    if s is None:
        return ''' <script>alert("invalid username or password");window.location='/'</script>'''
    elif s[3]=='admin':
        return ''' <script>alert("login successfully");window.location='/ahome'</script>'''
    elif s[3] == 'Hod':
        return ''' <script>alert("login successfully");window.location='/Hodhome'</script>'''
    else:
        return ''' <script>alert("invalid username or password");window.location='/'</script>'''




@obj.route('/ahome')
def ahome():
    return render_template('adminhome.html')


@obj.route('/deptmngmt')
def deptmngmt():
    cmd.execute("select * from `departmentmanagement`")
    s=cmd.fetchall()
    return render_template('deptmntmanagmt.html',val=s)


@obj.route('/deptadd',methods=['get','post'])
def deptadd():
    return render_template('deptadd.html')

@obj.route('/deptadd1',methods=['get','post'])
def deptadd1():
    dpt=request.form['textfield']
    cmd.execute("insert into departmentmanagement values (null,'"+dpt+"','pending')")
    con.commit()
    return ''' <script>alert("register successfully");window.location='/deptmngmt'</script>'''




@obj.route('/deptdelete',methods=['get','post'])
def deptdelete():
    id=request.args.get('id')
    cmd.execute("delete from departmentmanagement where Dept_id='"+str(id)+"'")
    con.commit()
    return ''' <script>alert("deleted successfully");window.location='/deptmngmt'</script>'''


@obj.route('/coursemngmt')
def coursemngmt():
    cmd.execute("select * from `departmentmanagement`")
    s=cmd.fetchall()
    return render_template('coursemanagmt.html',val1=s)



@obj.route('/coursemngmt1',methods=['get','post'])
def coursemngmt1():
    did=request.form['select']
    print(did,"diddd")
    btn=request.form['button']
    print(btn)
    if(btn=="Search"):
        cmd.execute("SELECT * FROM `coursemanagement` WHERE `Dept_id`='"+str(did)+"' AND  status='pending'")
        s1 = cmd.fetchall()
        cmd.execute("select * from `departmentmanagement`")
        s = cmd.fetchall()
        # cmd.execute("SELECT `departmentmanagement`.*,`coursemanagement`.`course_id`,`coursename` FROM `coursemanagement` JOIN `departmentmanagement` ON `departmentmanagement`.`Dept_id`=`coursemanagement`.`Dept_id`")
        # s1 = cmd.fetchall()
        return render_template('coursemanagmt.html', val=s1,val1=s)
    else:
        cmd.execute("select * from `departmentmanagement` where status='pending'")
        s = cmd.fetchall()
        return render_template("courseadd.html",val1=s)

@obj.route('/coursedel')
def coursedel():
    id=request.args.get('id')
    cmd.execute("update `coursemanagement` set status='1'  where course_id='"+id+"'")
    con.commit()
    return ''' <script>alert("deleted successfully");window.location='/coursemngmt'</script>'''






@obj.route('/courseadd',methods=['get','post'])
def courseadd():
    cmd.execute("select * from departmentmanagement")
    s=cmd.fetchall()
    return render_template('courseadd.html',val=s)

@obj.route('/courseadd1',methods=['get','post'])
def courseadd1():
    dep=request.form['select']
    print(dep)
    crs=request.form['textfield']
    seat = request.form['textfield1']
    cmd.execute("insert into coursemanagement values (null,'"+crs+"','"+seat+"','"+dep+"','pending')")
    con.commit()
    return ''' <script>alert("register successfully");window.location='/coursemngmt'</script>'''

@obj.route('/staffmngmt')
def staffmngmt():
    return render_template('staffmanagmnt.html')

@obj.route('/staff',methods=['get','post'])
def staff():
    type=request.form['select']
    print(type,"diddd")
    btn=request.form['button']
    if(btn=="Search"):
        cmd.execute("SELECT `staffmanagement`.*,`departmentmanagement`.* FROM `staffmanagement` JOIN `departmentmanagement` ON `staffmanagement`.`Dept_id`=`departmentmanagement`.`Dept_id` WHERE staffmanagement.`type`='"+str(type)+"'")
        s1 = cmd.fetchall()
        return render_template('staffmanagmnt.html',val=s1)
    else:
        return ''' <script>alert("invalid username or password");window.location='/staff'</script>'''


@obj.route('/staffreg',methods=['get','post'])
def staffreg():
    cmd.execute("SELECT * FROM `departmentmanagement` where status='pending'")
    s1 = cmd.fetchall()
    return render_template('regstaff.html',val=s1)

@obj.route('/staffmanagmnt',methods=['get','post'])
def staffmanagmnt():
    uname=request.form['uname']
    type=request.form['select']
    name=request.form['textfield']
    place=request.form['textfield1']
    phone=request.form['textfield2']
    pin=request.form['textfield3']
    gender=request.form['radio']
    dob=request.form['date']
    district=request.form['select1']
    state=request.form['select2']
    dname=request.form['select3']
    jdate=request.form['datee']
    experience=request.form['textfield4']
    qualification=request.files['fileField']
    fname=secure_filename(qualification.filename)
    qualification.save('static/image/' + fname)
    pwd=request.form['textfield5']
    cmd.execute("insert into login values (null,'"+type+"','"+uname+"','"+pwd+"')")
    lid=con.insert_id()
    cmd.execute("insert into staffmanagement values(null,'"+str(lid)+"','"+type+"','"+name+"','"+place+"','"+phone+"','"+pin+"','"+gender+"','"+dob+"','"+district+"','"+state+"','"+dname+"','"+jdate+"','"+experience+"','"+fname+"','pending')")
    con.commit()
    return ''' <script>alert("registered successfully");window.location='/staffmngmt'</script>'''


@obj.route('/staffdel')
def staffdel():
    id=request.args.get('id')
    cmd.execute("update `staffmanagement` set status='1'  where staff_id='"+id+"'")
    con.commit()
    return ''' <script>alert("deleted successfully");window.location='/staffmngmt'</script>'''


@obj.route('/staffedit')
def staffedit():
    id = request.args.get('id')
    session['id'] = id
    cmd.execute("select * from staffmanagement ")
    s = cmd.fetchone()
    return render_template('staffedit.html', val=s)


@obj.route('/staffupdate')
def staffupdate():
    return render_template('.html')

@obj.route('/questionadd',methods=['get','post'])
def questionadd():
    cmd.execute("select * from `subjectmanagement`")
    s = cmd.fetchall()
    return render_template('adminquestionadd.html',val1=s)


@obj.route('/questionadd1',methods=['get','post'])
def questionadd1():
    sid= request.form['select']
    question=request.form['textfield']
    opt1= request.form['textfield1']
    opt2= request.form['textfield2']
    opt3= request.form['textfield3']
    opt4= request.form['textfield4']
    ans = request.form['textarea']
    cmd.execute("insert into feedbackquestionmanagement values(null,'"+sid+"','"+question+"','"+opt1+"','"+opt2+"','"+opt3+"','"+opt4+"','"+ans+"','pending')")
    con.commit()
    return ''' <script>alert("added successfully");window.location='/questionview'</script>'''



@obj.route('/questionview')
def questionview():
    cmd.execute("SELECT * FROM `feedbackquestionmanagement` where status!='reject'")
    s=cmd.fetchall()
    return render_template('adminquestionviews.html',val1=s)


@obj.route('/questiondel')
def questiondel():
    id=request.args.get('id')
    cmd.execute("update `feedbackquestionmanagement` set status='reject'  where feedback_id='"+id+"'")
    con.commit()
    return ''' <script>alert("deleted successfully");window.location='/questionview'</script>'''

@obj.route('/questionedit', methods=['get','post'])
def questionedit():
    id = request.args.get('id')
    session['id']=id
    cmd.execute("select * from feedbackquestionmanagement ")
    s=cmd.fetchone()
    return render_template('adminquestionedit.html',val=s)

@obj.route('/questionupdate')
def questionupdate():
    question=request.form['textarea']
    cmd.execute("update `feedbackquestionmanagement` set status='reject'  where feedback_id='"+str(session['id'])+"'")
    con.commit()
    return render_template('adminquestionedit.html.html')

# @obj.route('/seatview',methods=['post','get'])
# def seatview():
#     cmd.execute("select * from departmentmanagement")
#     s = cmd.fetchall()
#     cmd.execute("select * from coursemanagement")
#     s1 = cmd.fetchall()
#     return render_template('adminseatview.html', val=s,val1=s1)

# @obj.route('/seatview1',methods=['post','get'])
# def seatview1():
#  did=request.form['select']
#  print(did,"diddd")
#  btn=request.form['button']
#  print(btn)
#  if(btn=="Search"):
#         cmd.execute("SELECT * FROM `addavailableseats` WHERE `Dept_id`='"+str(did)+"'")
#         s1 = cmd.fetchall()
#         cmd.execute("select * from departmentmanagement")
#         s = cmd.fetchall()
#         # cmd.execute("SELECT `departmentmanagement`.*,`coursemanagement`.`course_id`,`coursename` FROM `coursemanagement` JOIN `departmentmanagement` ON `departmentmanagement`.`Dept_id`=`coursemanagement`.`Dept_id`")
#         # s1 = cmd.fetchall()
#         return render_template('adminseatview.html', val=s,val1=s1)
#  else:
#         cmd.execute("select * from `addavailableseats` ")
#         s = cmd.fetchall()
#         return render_template("adminseatview.html",val1=s)

# @obj.route('/seatadd',methods=['post','get'])
# def seatadd():
#     cmd.execute("select * from departmentmanagement")
#     s = cmd.fetchall()
#     cmd.execute("select * from coursemanagement")
#     s1 = cmd.fetchall()
#     return render_template('adminseatadd.html',val=s, val1=s1)




# @obj.route('/seatadd1',methods=['post','get'])
# def seatadd1():
#     dpt=request.form['select']
#     crs=request.form['select1']
#     seat=request.form['textfield']
#
#     cmd.execute("insert into addavailableseats values(null,'"+dpt+"','"+crs+"','"+seat+"')")
#     con.commit()
#     return ''' <script>alert(" added successfully");window.location='/seatview'</script>'''
#
#
# @obj.route('/seatedit', methods=['get','post'])
# def seatedit():
#     id = request.args.get('id')
#     session['id']=id
#     cmd.execute("select * from addavailableseats ")
#     s=cmd.fetchone()
#     return render_template('adminseatedit.html',val=s)
#
# @obj.route('/seatupdate')
# def seatupdate():
#     seat=request.form['textfield']
#     cmd.execute("update `addavailableseat` set status='reject'  where seat_id='"+str(session['id'])+"'")
#     con.commit()
#     return render_template('adminseatedit.html')


@obj.route('/complaintview')
def complaintview():
    return render_template('admincomplaintview.html')

@obj.route('/complaintview1',methods=['get','post'])
def complaintview1():
    type= request.form['select']
    print(type)
    if (type == "Student"):
        cmd.execute("SELECT viewandreplycomplaint.* ,studentmanagment.name from viewandreplycomplaint join studentmanagment ON viewandreplycomplaint.type_id=studentmanagment.login_id where viewandreplycomplaint.reply='pending'")
        s = cmd.fetchall()
        print(s)
        return render_template('admincomplaintview.html',val=s)
    else:
        cmd.execute("SELECT viewandreplycomplaint.* ,parentregistration.name from viewandreplycomplaint join parentregistration ON viewandreplycomplaint.type_id=parentregistration.login_id where viewandreplycomplaint.reply='pending'")
        s= cmd.fetchall()
        print(s)
        return render_template('admincomplaintview.html', val=s)


@obj.route('/complaintreply')
def complaintreply():
    id = request.args.get('id')
    session['id'] = id
    cmd.execute("SELECT complaint FROM `viewandreplycomplaint` WHERE `complaint_id`='" + str(id) + "'")
    s = cmd.fetchone()
    return render_template('adminreplycompliant.html',val=s[0])

# @obj.route('/complaintreply1',methods=['post'])
# def complaintreply1():
#
#     return render_template('adminreplycompliant.html')

@obj.route('/complaintreply2',methods=['post'])
def complaintreply2():
    reply = request.form['textfield2']
    cmd.execute("update `viewandreplycomplaint` set reply='"+reply+"'  where complaint_id='" + str(session['id']) + "'")
    con.commit()
    # return render_template('adminreplycomplaint.html')
    return ''' <script>alert(" replied successfully");window.location='/complaintview'</script>'''


@obj.route('/hodhome')
def hodhome():
    return render_template('Hodhome.html')

@obj.route('/hodstudaccept')
def hodstudaccept():
    return render_template('Hodstudaccept.html')

@obj.route('/hodsubmngmt')
def hodsubmngmt():
    return render_template('Hodsubmngmt.html')

@obj.route('/hodsubview')
def hodsubview():
    return render_template('hodsubview.html')


obj.run(debug=True)