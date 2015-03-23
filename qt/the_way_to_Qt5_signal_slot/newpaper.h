#ifndef NEWPAPER_H
#define NEWPAPER_H
#endif // NEWPAPER_H

#include <QObject>

class Newspaper : public QObject
{
    Q_OBJECT
public:
    Newspaper(const QString & name): m_name(name)
    {
    }

    void send()
    {
        emit newPaper(m_name);
    }

signals:
    void newPaper(const QString &name);

private:
    QString m_name;
};