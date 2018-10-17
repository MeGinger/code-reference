// Rotate Image 
// clockwise by 90 degrees
1. reverse up to down AND swap the symmetry

// clockwise by 180 degrees
1. reverse up to down AND reverse left to right
2. reverse left to right AND reverse up to down

// clockwise by 270 degrees
1. reverse left to right AND swap the symmetry


// clockwise by 90 degrees
public void rotate(int[][] matrix) {
    if (matrix == null || matrix.length == 0|| matrix[0].length == 0) {
        return;
    }
    
    int r = matrix.length;
    int c = matrix[0].length;
    
    // reverse up to down - one linear loop
    for (int first = 0, last = r - 1; first < last; first++, last--) {
        int[] temp = matrix[first];
        matrix[first] = matrix[last];
        matrix[last] = temp;
    } 
    // reverse left to right - double for loops

    // swap the symmetry - diagonal
    for (int i = 0; i < r; i++) {
        for (int j = i + 1; j < c; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
}

Spiral Matrix

class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return res;
        }
        
        int top = 0, bottom = matrix.length - 1; // row - i 
        int left = 0, right = matrix[0].length - 1; // column - j
        
        while (true) {
            for (int i = left; i <= right; i++) {
                res.add(matrix[top][i]);
            }
            top++;
            if (top > bottom || left > right) break;
            
            for (int i = top; i <= bottom; i++) {
                res.add(matrix[i][right]);
            }
            right--;
            if (top > bottom || left > right) break;
            
            for (int i = right; i >= left; i--) {
                res.add(matrix[bottom][i]);
            }
            bottom--;
            if (top > bottom || left > right) break;
            
            for (int i = bottom; i >= top; i--) {
                res.add(matrix[i][left]);
            }
            left++;
            if (top > bottom || left > right) break;
        }
        
        return res;
    }
}

Set Matrix Zeroes

class Solution {
    public void setZeroes(int[][] matrix) {
        // invalid input
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return;
            // // throw new IllegalArgumentException();
        }
        
        // constant space 
        int col0 = 1, row = matrix.length, col = matrix[0].length;
        
        for (int i = 0; i < row; i++) {
            if (matrix[i][0] == 0) {
                col0 = 0;
            }// The reason we treat this the first col separately is that...
              /* we use first col [0...length - 1] to indicate the whole row should be populated as 0
                 matrix[2][0] == 0 -> the 3rd row ...
                 
                 use first row [1...length - 1] to indicate the whole col should be populated as 0
                 matrix[0][2] == 0 -> the 3rd col ...
                 
                 in this case, the 1st col indicator is missing, 
                 so I use a single variable as the indicator, only constant space is needed
                 
               */
            for (int j = 1; j < col; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        
        for (int i = row - 1; i >= 0; i--) {            
            for (int j = col - 1; j >= 1; j--) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }                                   
            }
            if (col0 == 0) { // put it after loop, NOT before loop
                matrix[i][0] = 0;
            }
        }
    }
}