package main

import (
	"fmt"
	"math/rand"
)

type hole chan int

func deepspace(w hole) {
	w <- rand.Int()
}

func main() {
	n := 8
	w := make(hole, n)

	// Map
	for i := 0; i < n; i++ {
		go deepspace(w)
	}

	// Reduce
	t := 0
	for i := 0; i < n; i++ {
		t += <-w
	}
	fmt.Println("Total: ", t)
}
