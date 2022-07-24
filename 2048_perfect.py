import turtle, random


class BackGround(turtle.Turtle):  #類を定義し、数字タイル以外の図を作成
    def __init__(self):
        super().__init__()
        self.penup()
        self.ht()

    def draw_block(self):
        self.shape('bg.gif')  # 背景タイルを作成
        for i in allpos:
            self.goto(i)
            self.stamp()
        self.color('white', 'white')  # 他の背景を描く
        self.goto(-215, 120)
        self.begin_fill()
        self.goto(215, 120)
        self.goto(215, 110)
        self.goto(-215, 110)
        self.end_fill()
        self.shape('title.gif')
        self.goto(-125, 210)
        self.stamp()
        self.shape('score.gif')
        self.goto(125, 245)
        self.stamp()
        self.shape('top_score.gif')
        self.goto(125, 170)
        self.stamp()

    def judge(self):  # ゲーム失敗と2048を達成した提示文字
        global flag_win, flag_win_lose_text
        self.color('blue')
        judge = 0  # 移動できるかどうかの判断
        for i in block_dic.values():
            for j in block_dic.values():
                if i.num == 0 or i.num == j.num and i.distance(j) == 100:
                    judge += 1
        if judge == 0:  # 移動できないからゲーム失敗
            self.write('     GAME OVER\nもう一回遊ぶとスペースきーを押してください', align='center', font=('黑体', 30, 'bold'))
            flag_win_lose_text = False
        if flag_win is True:  # 1回だけ判断する
            for k in block_dic.values():
                if k.num == 2048:  # ゲーム達成
                    flag_win = False
                    self.write('     2048達成\n続けて遊ぶとスペースきーを押してください', align='center', font=('黑体', 30, 'bold'))
                    flag_win_lose_text = False

    def win_lose_clear(self):  # ゲーム失敗と達成の2048提示文字clear
        global flag_win_lose_text
        self.clear()
        flag_win_lose_text = True

    def show_score(self):  # 得点の表示
        global score, top_score
        if score > top_score:
            top_score = score
            with open('.\\score.txt', 'w') as f:
                f.write(f'{top_score}')
        self.color('white')
        self.goto(125, 210)
        self.clear()
        self.write(f'{score}', align='center', font=('Arial', 20, 'bold'))
        self.goto(125, 135)
        self.write(f'{top_score}', align='center', font=('Arial', 20, 'bold'))


class Block(turtle.Turtle):  # 数字タイル類
    def __init__(self):
        super().__init__()
        self.ht()
        self.penup()
        self.num = 0

    def draw(self):
        self.clear()
        dic_draw = {2: '#eee6db', 4: '#efe0cd', 8: '#f5af7b',
                    16: '#fb9660', 32: '#f57d5a', 64: '#f95c3d',
                    128: '#eccc75', 256: '#eece61', 512: '#efc853',
                    1024: '#ebc53c', 2048: '#eec430', 4096: '#aeb879',
                    8192: '#aab767', 16384: '#a6b74f'}
        if self.num > 0:  # 数字が0より大きい、タイルを描く
            self.color(f'{dic_draw[self.num]}')  # 色を選択する
            self.begin_fill()
            self.goto(self.xcor()+48, self.ycor()+48)
            self.goto(self.xcor()-96, self.ycor())
            self.goto(self.xcor(), self.ycor()-96)
            self.goto(self.xcor()+96, self.ycor())
            self.goto(self.xcor(), self.ycor()+96)
            self.end_fill()
            self.goto(self.xcor()-48, self.ycor()-68)
            if self.num > 4:  # 数字の大きさによって色を選択する
                self.color('white')
            else:
                self.color('#6d6058')
            self.write(f'{self.num}', align='center', font=('Arial', 27, 'bold'))
            self.goto(self.xcor(), self.ycor()+20)


class Game():
    def init(self):
        back = BackGround()   # ゲームの背景を描く
        back.draw_block()
        for i in allpos:
            block = Block()
            block.goto(i)
            block_dic[i] = block
        game.grow()

    def restart(self):  # もう一回遊ぶ
        global score, flag_win_lose_text
        score = 0
        for i in block_dic.values():
            i.num = 0
            i.clear()
        win_lose_text.clear()
        game.grow()
        flag_win_lose_text = True  # ゲーム失敗と達成の判断文字，clearされた後にmove

    def grow(self):  # 2と4の数字タイルをランダムで発生
        block_list = []
        for i in allpos:
            if block_dic[i].num == 0:
                block_list.append(block_dic[i])
        turtle_choice = random.choice(block_list)
        turtle_choice.num = random.choice([2, 2, 2, 2, 4])  # num=2/4
        turtle_choice.draw()
        win_lose_text.judge()
        show_score_text.show_score()
        ms.update()

    def move_up(self):
        allpos1 = allpos[::4]  # 4列で分けて
        allpos2 = allpos[1::4]
        allpos3 = allpos[2::4]
        allpos4 = allpos[3::4]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_down(self):
        allpos1 = allpos[-4::-4]
        allpos2 = allpos[-3::-4]
        allpos3 = allpos[-2::-4]
        allpos4 = allpos[-1::-4]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_left(self):
        allpos1 = allpos[:4]
        allpos2 = allpos[4:8]
        allpos3 = allpos[8:12]
        allpos4 = allpos[12:16]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_right(self):
        allpos1 = allpos[-1:-5:-1]
        allpos2 = allpos[-5:-9:-1]
        allpos3 = allpos[-9:-13:-1]
        allpos4 = allpos[-13:-17:-1]
        self.move_move(allpos1, allpos2, allpos3, allpos4)

    def move_move(self, allpos1, allpos2, allpos3, allpos4):
        if flag_win_lose_text is True:
            count1 = self.move(allpos1)  # 4列＆4行で移動
            count2 = self.move(allpos2)
            count3 = self.move(allpos3)
            count4 = self.move(allpos4)
            if count1 or count2 or count3 or count4:  # タイル移動されたかどうかを判断
                self.grow()

    def move(self, pos_list):
        num_list = []
        for i in pos_list:
            num_list.append(block_dic[i].num)  #  NUMをlistに
        new_num_list, count = self.list_oper(num_list)  #  ただlist_operで新しいlistを作る
        for j in range(len(new_num_list)):  # 新しいlistの値をあげて.num　draw()方法を使う
            block_dic[pos_list[j]].num = new_num_list[j]
            block_dic[pos_list[j]].draw()
        return count

    def list_oper(self, num_list):  # num_listの操作，【2,0,2,2】にすると
        global score
        count = True
        temp = []
        new_temp = []
        for j in num_list:
            if j != 0:
                temp.append(j)  # temp=[2,2,2]
        flag = True
        for k in range(len(temp)):
            if flag:
                if k < len(temp)-1 and temp[k] == temp[k+1]:
                    new_temp.append(temp[k]*2)
                    flag = False
                    score += temp[k]
                else:
                    new_temp.append(temp[k])  # new_temp=[4,2]
            else:
                flag = True
        for m in range(len(num_list)-len(new_temp)):
            new_temp.append(0)  # new_temp=[4,2,0,0]
        if new_temp == num_list:
            count = False  # num_list変化かどうかを判断，数字タイルが移動していない
        return(new_temp, count)


if __name__ == '__main__':
    ms = turtle.Screen()  # メインウィンドウの設置
    ms.setup(430, 630, 400, 50)
    ms.bgcolor('gray')
    ms.title('2048')
    ms.tracer(0)
    ms.register_shape('bg.gif')
    ms.register_shape('title.gif')
    ms.register_shape('score.gif')
    ms.register_shape('top_score.gif')
    block_dic = {}  # 数字タイルの辞書，位置をkey
    allpos = [(-150, 50), (-50, 50), (50, 50), (150, 50),
              (-150, -50), (-50, -50), (50, -50), (150, -50),
              (-150, -150), (-50, -150), (50, -150), (150, -150),
              (-150, -250), (-50, -250), (50, -250), (150, -250)]
    flag_win = True  # 2048を達成する判断　文字を1回だけ出ていく
    flag_win_lose_text = True
    score = 0
    with open('.\\score.txt', 'r') as f:
        top_score = int(f.read())  #  scoreのデータを読み込む
    show_score_text = BackGround()
    win_lose_text = BackGround()
    game = Game()
    game.init()

    ms.listen()
    ms.onkey(game.move_up, 'Up')
    ms.onkey(game.move_down, 'Down')
    ms.onkey(game.move_left, 'Left')
    ms.onkey(game.move_right, 'Right')
    ms.onkey(win_lose_text.win_lose_clear, 'Return')
    ms.onkey(game.restart, 'space')

    ms.mainloop()

