# ColReorder

ColReorder adds the ability for the end user to click and drag column headers to reorder a table as they see fit, to DataTables. Key features include:

* Very easy integration with DataTables
* Tight integration with all other DataTables plug-ins
* The ability to exclude the first (or more) column from being movable
* Predefine a column order
* Save staving integration with DataTables


# Installation

To use ColReorder, first download DataTables ( http://datatables.net/download ) and place the unzipped ColReorder /*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package into a `extensions` directory in the DataTables /*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package. This will allow the pages in the examples to operate correctly. To see the examples running, open the `examples` directory in your web-browser.


# Basic usage

ColReorder is initialised using the `$.fn.dataTable.ColReorder` constructor. For example:

```js
$(document).ready( function () {
    $('#example').DataTable();

    new $.fn.dataTable.ColReorder( table );
} );
```


# Documentation / support

* Documentation: http://datatables.net/extensions/colreorder/
* DataTables support forums: http://datatables.net/forums


# GitHub

If you fancy getting involved with the development of ColReorder and help make it better, please refer to its GitHub repo: https://github.com/DataTables/ColReorder

