<?php
trait ezcReflectionReturnInfo
{
    function getReturnType() { /*1*/ }
    function getReturnDescription() { /*2*/ }
}

class ezcReflectionMethod extends ezcReflectionMethod
{
    use ezcReflectionReturnInfo;
    /* ... */
}

class ezcReflectionFunction extends ezcReflectionFunction
{
    use ezcReflectionReturnInfo;
    /* ... */
}

?>
