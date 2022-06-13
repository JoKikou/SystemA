#SystemA made by 1CES2205 徐キ晧
import pandas as pd
import csv
df=pd.read_csv('FEH_00200521_220610143730.csv',usecols=[2,3,4])
df=df.drop(index=0)
#print(df)

for col in df.columns:
    print(col)
    s1=df[col]
    s1=s1.map(lambda x:None if x=='-' else x.replace(',','')).astype(float)

    print(f'\t平均値:{s1.mean():.0f}')
    print(f'\t中央値:{s1.median():.0f}')
    print(f'\t最大値:{s1.max():.0f}')
    print(f'\t最小値:{s1.min():.0f}')
    print(f'\t分散:{s1.var():.0f}')
    print(f'\t標準偏差:{s1.std():.0f}')

