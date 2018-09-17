

import numpy as np 
import pandas as pd 
import matplotlib.pyplot as plt

df = pd.read_csv('dados.csv',index_col = 'geracao')
for col in df.columns:
	#plt.figure()
	plt.plot(df.index,df[col])
	#plt.title(col)
	#plt.savefig('imgs/'+col+'.png')

plt.legend(df.columns)

plt.savefig('imgs/todos.png')
plt.show()
