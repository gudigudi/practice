#include "dialog.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    Dialog *dialog = new Dialog();
    dialog->show();

    return a.exec();
}
