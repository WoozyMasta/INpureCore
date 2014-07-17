/// <reference path="imports.d.ts"/>

class Greeter {
    constructor(public greeting: string) { }
    greet() {
        return this.greeting;
    }
};
var greeter = new Greeter("This text is from the javascript engine running compiled typescript!");
out.print(greeter.greet());
