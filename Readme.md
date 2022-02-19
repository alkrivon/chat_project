# ������ ���� � �����

������ �������� � ������ Java-���������� �� �������� SimbirSoft.

# ���������

+ User - �������������� ������������, ������� ����� �������� � ����;
+ Moderator - �������������� ������������, � ������������ �������;
+ Admin - �������������� ������������, � ������������ �������;
+ Room (private_status = 1) - ����� ��� ��� 2-� � ����� �������������;
+ Room (private_status = 0) - ��� �� 2 �������;
+ Message - �����, ���������� ������������� � ����;
+ ����������� ������� - ����� ������������ �� '//' � ��������� ���������
  ������������ ��������;

+ ���-��� - ����������� ������������, ��� �������� ��������� �������� �����������
  ����������� �������;

# ����������

+ ������������

| ������������/��������� �������� | User | Moderator | Admin |
|---------------------------------|------|-----------|-------|
| ���������� �������������        | -    | +         | +     |
| ������������� �������������     | -    | +         | +     |
| ���������� �����������          | -    | -         | +     |
| �������� �����������            | -    | -         | +     |

+ ���������

| ������������/��������� �������� | User | Moderator | Admin | ��������������� <br/>������������ |
|---------------------------------|------|-----------|-------|-----------------------------------|
| �������� ���������              | +    | +         | +     | -                                 |
| �������� ���������              | +    | +         | +     | +                                 |
| �������� ���������              | -    | +         | +     | -                                 |

+ �������

| ������������/��������� �������� | �������� ������� | User | Moderator | Admin | ��������������� ������������ |
|---------------------------------|------------------|------|-----------|-------|------------------------------|
| �������� ����� ������           | +                | +    | +         | +     | -                            |
| �������� ��������� ������       | +                | +    | +         | +     | -                            |
| ���������� �������������        | +                | +    | +         | +     | -                            |
| �������� �������������          | +                | -    | -         | +     | -                            |
| �������������� �������          | +                | -    | -         | +     | -                            |

+ ������� ��� ����:

1. //room create{�������� �������} - �������� �������;
2. //room remove{�������� �������} - �������� �������;
3. //room rename{�������� �������} - �������������� �������;
4. //room addUser{�������� �������}-l{��� ������������} - �������� ������������ � �������;
5. //room deleteUser{�������� �������}-l{��� ������������} - ������� ������������ �� �������;
6. //user ban -l{�������� �������}-m{���-�� �����} - ������������� ������������;
7. //yBot find -v{id ����� �� YouTube} - �������� ���-�� ���������� ������� �����;
8. //yBot find -l{id ����� �� YouTube} - �������� ���-�� ������ ������� �����;
9. //yBot find -v -l{id ����� �� YouTube} - �������� ���-�� ���������� � ������ �����;
10. //yBotChannelInfo{id ������ �� YouTube} - �������� �������� ������ � ������ �� ������ 5 �����;

# ���� ������

� �������� �� ������������ MySQL 8.0.24 � �����������:

+ db_user: AlexKriv
+ db_pass: AlexKriv
+ db_host: localhost:3306
+ db_name: chat

������� � �� ������������� ������������� ��� ������ ������� ����������;

