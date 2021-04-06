#!/bin/bash

sudo yum install vsftpd -y
sudo systemctl start vsftpd
sudo systemctl enable vsftpd


sed -i -e '/anonymous_enable/s/YES/NO/g' /etc/vsftpd/vsftpd.conf

echo "chroot_local_user=YES" >> /etc/vsftpd/vsftpd.conf
echo "allow_writeable_chroot=YES" >> /etc/vsftpd/vsftpd.conf

sudo systemctl restart vsftpd

sudo useradd anuuu

echo "anuuu:12345678" | chpasswd

cd /home/anuuu
touch config.txt
