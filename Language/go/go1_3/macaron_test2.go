package main

import (
	"github.com/Unknwon/macaron"
	"log"
	"net/http"
)

func main() {
	m := macaron.Classic()
	m.Get("/", myHandler)

	log.Println("Server is running...")
	log.Panicln(http.ListenAndServe("0.0.0.0:4000", m))
}

func myHandler(ctx *macaron.Context) string {
	return "the request path is: " + ctx.Req.RequestURI
}
