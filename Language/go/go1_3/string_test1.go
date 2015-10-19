package main

import (
	"fmt"
)

func main() {
	str := "Hello, 世界"
	n := len(str)
	for i := 0; i < n; i++ {
		ch := str[i]
		fmt.Println(i, ch)
	}
	fmt.Println()
	for i, ch := range str {
		fmt.Println(i, ch) // ch的类型为rune
	}

}
