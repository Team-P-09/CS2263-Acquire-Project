/*
 * MIT License
 *
 * Copyright (c) 2021 Thomas Evans, David Lindeman, and Natalia Castaneda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class Tile {
    int row ;
    int col;
    String corp;
    String location;
    boolean status;

    public Tile(int r, int c){
        row = r;
        col = c;
        location = String.valueOf(col) + ", " + String.valueOf(row);
        corp = null;
        status = false;
    }

//    public int getCol(){
//        return col;
//    }
//
//    public String getLocation(){
//        String location = this.getCol() + ", " + this.getRow();
//        return location;
//    }
//
//    public String getCorp(){
//        return corp;
//    }
//
//    public void setRow(int y){
//        if (y >= 1 && y <= 9){
//            row = y - 1;
//        }
//        else{
//            row = row;
//        }
//    }
//
//    public void setCol(int x){
//        if (x >= 1 && x <= 12){
//            col = x - 1;
//        }
//        else{
//            col = col;
//        }
//    }
//
//    public void setCorp(String name){
//        corp = name;
//    }

    public void activateTile(){
        status = true;
    }

}
