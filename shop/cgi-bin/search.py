#!/usr/bin/env python3
import cgi
import webbrowser
our_form = cgi.FieldStorage()
search = our_form.getfirst("search","не задано")
print("Content-type: text/html")
print()
if search == 'Дрель':
    webbrowser.open("http://localhost:8000/assets/kat/drills.html",0,True)
else:
    print('Ничего не найдено')
