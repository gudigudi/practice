#!/usr/bin/env python3
# encoding: utf-8

try:
    raise Exception('spam', 'egg')
except Exception as inst:
    print(type(inst))   # the exception instance
    print(inst.args)    # arguments stored in .args
    print(inst)         # __str__ allows args to be printed directly,
                        # but may be overridden in exception subclasses
    x, y = inst.args    # uppack args
    print('x = ', x)
    print('y = ', y)

def this_fails():
    x = 1/0

try:
    this_fails()
except ZeroDivisionError as err:
    print('Handling run-time error:', err)