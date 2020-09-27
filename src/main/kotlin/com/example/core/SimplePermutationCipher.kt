package com.example.core

import java.io.File

class  SimplePermutationCipher {
    companion object {
        private  val ENCODE_PERMUTATION:IntArray = intArrayOf(2,3,5,4,1,0);
        private  val DECODE_PERMUTATION:IntArray = intArrayOf(5,4,0,1,3,2);

        fun encodeFile(path: String){
            val bytes: CharArray = File(path).readText().toCharArray()
            val encodeBytes:String
            encodeBytes = encode(bytes)
            File(path).writeText(encodeBytes)
        }
        fun decodeFile(path: String){
            val bytes: CharArray = File(path).readText().toCharArray()
            val decodeBytes:String
            decodeBytes = decode(bytes)
            File(path).writeText(decodeBytes)
        }
        private fun encodePermutationBuff(buff:CharArray):String {
            val encodeBuff: CharArray = CharArray(buff.size)
            ENCODE_PERMUTATION.forEachIndexed { index,encodeIndex ->encodeBuff[index] = buff[encodeIndex]  }
            return String(encodeBuff)
        }
        private fun decodePermutationBuff(buff:CharArray):String {
            val decodeBuff: CharArray = CharArray(buff.size)
            DECODE_PERMUTATION.forEachIndexed { index,encodeIndex ->decodeBuff[index] = buff[encodeIndex]  }
            return String(decodeBuff)
        }
        private fun encode(bytes:CharArray): String {
            val buffSize = 6
            var buff:CharArray = CharArray(buffSize)
            val fakeCharCount: Int = bytes.size.rem(buffSize)
            var encodeBytes:String = ""
            if (fakeCharCount != 0) {encodeBytes.padStart(fakeCharCount,'\u0000')}
            for (i in 0..(bytes.size-1)){
                if (i!=0 && i.rem(buffSize) == 0 ){
                    encodeBytes += encodePermutationBuff(buff)
                    buff = CharArray(buffSize)
                }
                buff[i.rem(buffSize)] = bytes[i]
                if (i == bytes.size-1){
                    encodeBytes += encodePermutationBuff(buff)
                    buff = CharArray(buffSize)
                }
            }
            return  encodeBytes
        }
        private fun decode(bytes:CharArray): String {
            val buffSize = 6
            var buff:CharArray = CharArray(buffSize)
            var decodeBytes:String = ""
            for (i in 0..(bytes.size-1)){
                if (i!=0 && i.rem(buffSize) == 0){
                    decodeBytes += decodePermutationBuff(buff)
                    buff = CharArray(buffSize)
                }
                buff[i.rem(buffSize)] = bytes[i]
                if (i == bytes.size-1){
                    decodeBytes += decodePermutationBuff(buff)
                    buff = CharArray(buffSize)
                }
            }
            return  decodeBytes
        }
    }
}