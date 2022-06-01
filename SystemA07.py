#SystemA made by 1ces2205 JoKikou
from tkinter import *
root = Tk()
root.title("システム開発A")
root.geometry("350x180+500+300")
lb = Label(root, text="txtファイルの位置を入力してください：")
lb.grid(row=0, column=0)
entry = Entry(root)
entry.grid(row=0, column=1)
a = ['*', '-', '/', '=', '>', '<', '>=', '=', '<=', '+','<>']
b = ['(', ')', '"', ';', '!']
c = ['long','float','static',
   'char','short','switch','int',
   'const','if','then','else','for'
   ,'while','break']

#文字判断
def IsLetter(Char):
   if ((Char >= 'a' and Char <= 'z') or (Char >= 'A' and Char <= 'Z')):
      return True
   else:
      return False

#数字判断
def IsDigit(Char):
   if (Char <= '9' and Char >= '0'):
      return True
   else:
      return False

#スペース判断
def IsSpace(Char):
   if (Char == ' '):
      return True
   else:
      return False

def fenli(List):
   ResultList = []
   for String in List:
      Letter = ''
      letter = ''
      index = 0
      for Char in String:
         if (index < len(String) - 1):
            index += 1
         if (IsLetter(Char) or IsDigit(Char)):
            if (IsLetter(String[index]) or IsDigit(String[index])):
               Letter += Char
            elif (IsSpace(String[index]) or (String[index] in b) or (
                    String[index] in a) or (String[index:index + 2] in a)):
               Letter += Char
               ResultList.append(Letter)
               Letter = ''
         elif Char in b:
                     ResultList.append(Char)
         elif Char in a:
                     letter += Char
                     if (String[index] in a):
                           letter += String[index]
                           ResultList.append(letter)
                           letter = ''
                     else:
                           ResultList.append(letter)
                           letter = ''
         elif IsSpace(Char):
                           pass
   return ResultList

def panduan(char):
   for i in char:
      if i in a:
         print("<演算子,"+i+">")
      elif i in b:
         print("<符号,"+i+">")
      elif i in c:
         print("<キーワード,"+i+">")
      elif i.isdigit():
         print("<常数,"+i+">")
      elif i.isalnum():
         print("<プログラムコード," + i + ">")

def main():
   p=[]
   with open(entry.get(),'r') as f:
      m=f.readlines()
      for i in m:
         n=i.strip()
         p.append(n)
      y=fenli(p)
      panduan(y)
btn = Button(root, text="スタート", fg='black', relief="raised", bd="9", command=main)
btn.grid(row=1, column=1)
root.mainloop()