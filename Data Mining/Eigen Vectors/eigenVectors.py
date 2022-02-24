# S21-ICSI431: Data Mining
# Homwork 4 - 1(c): Eigen vectors
# 05-05-2021
# Jo Eun Kim
# Python 3.9.1

import numpy as np
import matplotlib.pyplot as plt
import scipy.linalg as la

l = np.array([[5,-2,-3,0,0,0], [-2,5,-3,0,0,0], [-3,-3,10,-4,0,0], [0,0,-4,24,-10,-10], [0,0,0,-10,110,-100], [0,0,0,-10,-100,110]])
print("L = ")
print(l)
eigenDecomp = la.eig(l) # compute the eigen decomposition of l
print("Eigenvalues of L = ")
print(eigenDecomp[0]) # eigenDecomp[0]=eigenvalues, eigenDecomp[1]=eigenvectors
print("Smallest non-zero eigenvalue of L and its index = ")
for i in eigenDecomp[0]: # set the 1st element that is greater than 10^(-13) as initial minEigval
	if i > 10 ** -13:
		minEigval = i
		break
indexOfMinEigval = 0 # increase by 1 if new min found
for i in eigenDecomp[0]: # find the index of the actual smallest element that is greater than 10^(-13)
	if i < minEigval: # new min found
		indexOfMinEigval = indexOfMinEigval + 1 # index++
		if i > 10 ** -13: # update minEigval only if > 10^(-13)
			minEigval = i
			indexOfMinEigval = indexOfMinEigval
print(minEigval)
print(indexOfMinEigval)

print("eigenvector corresponding to the smallest non-zero eigenvalue = ")
# the position of an eigenvalue in the array eigenDecomp[0] correspond to the column in eigenDecomp[1] with its eigenvector
u = (eigenDecomp[1])[:,indexOfMinEigval].reshape(6,1) # n=6
print(u)

print("Plot = ")
xAxis = [1,2,3,4,5,6]
yAxis1 = np.array(((u[0])[0], (u[1])[0], (u[2])[0], (u[3])[0], (u[4])[0], (u[5])[0]))
print(xAxis)
print(yAxis1)
plt.plot(xAxis, yAxis1, 'bo')
plt.show()


print("\n-----------------------------------------------------------------")
ls = np.array([[1,-0.4,-0.424,0,0,0], [-0.4,1,-0.424,0,0,0], [-0.424,-0.424,1,-0.258,0,0], [0,0,-0.258,1,-0.194,-0.194], [0,0,0,-0.194,1,-0.903], [0,0,0,-0.194,-0.903,1]], dtype = complex)
print("Ls = ")
print(ls)
eigenDecomp2 = la.eig(ls) # compute the eigen decomposition of ls
print("Eigenvalues of Ls = ")
print(eigenDecomp2[0]) # eigenDecomp2[0]=eigenvalues, eigenDecomp2[1]=eigenvectors
print("Smallest non-zero eigenvalue of L and its index = ")
for i in eigenDecomp2[0]: # set the 1st element that is greater than 10^(-13) as initial minEigval2
	if i > 10 ** -13:
		minEigval2 = i
		break
indexOfMinEigval2 = 0 # increase by 1 if new min found
for i in eigenDecomp2[0]: # find the index of the smallest element that is greater than 10^(-13) as minEgval
	if i < minEigval2: # new min found
		indexOfMinEigval2 = indexOfMinEigval2 + 1 # index++
		if i > 10 ** -13: # update minEigval2 only if > 10^(-13)
			minEigval2 = i
			indexOfMinEigval2 = indexOfMinEigval2
print(minEigval2)
print(indexOfMinEigval2)

print("eigenvector corresponding to the smallest non-zero eigenvalue = ")
# the position of an eigenvalue in the array eigenDecomp2[0] correspond to the column in eigenDecomp2[1] with its eigenvector
us = (eigenDecomp2[1])[:,indexOfMinEigval2].reshape(6,1) # n=6
print(us)

print("Plot = ")
yAxis2 = np.array(((us[0])[0], (us[1])[0], (us[2])[0], (us[3])[0], (us[4])[0], (us[5])[0]))
print(xAxis)
print(yAxis2)
plt.plot(xAxis, yAxis2, 'bo')
plt.show()