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

import java.util.ArrayList;
import java.util.HashMap;

@Getter @Setter
public class Wallet {
    //instance variables
    int cash;
    HashMap<String, Integer> stocks;
    //contains corp name and quantity of stocks in that corp

    //Constructor method
    public Wallet(ArrayList<String> corpNames){
        cash = 6000;
        stocks = initWallet(corpNames);
    }

    private HashMap<String, Integer> initWallet(ArrayList<String> corpNames){
        HashMap<String, Integer> newWallet = new HashMap<>();
        for(String cName : corpNames){
            newWallet.put(cName, 0);
        }
        return newWallet;
    }

    public void addCash(Integer value){
        setCash(getCash() + value);
    }

    public void removeCash(Integer value){
        setCash(getCash() - value);
    }

    public void addStock(String cName, Integer qty){
        Integer newQty = getStocks().get(cName) + qty;
        getStocks().put(cName, newQty);
    }

    public void removeStock(String cName, Integer qty){
        Integer newQty = getStocks().get(cName) - qty;
        getStocks().put(cName, newQty);
    }
}
