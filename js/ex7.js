// 声明一个对象base
function Base(name) {
    this.name = name;
    this.getName = function() {
        return this.name;
    }
}

// 声明一个对象child
function Child(id) {
    this.id = id;
    this.getId = function() {
        return this.id;
    }
}

// 将child的原型指向一个新的base对象
Child.prototype = new Base("base");

// 实例化一个child对象
var c1 = new Child("child");

// c1本身具有getId方法
console.log(c1.getId());
// 由于c1从原型链上"继承"到了getName方法, 因此可以访问
console.log(c1.getName());