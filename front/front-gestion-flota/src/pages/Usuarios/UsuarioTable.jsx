import Table from "../../components/Table/Table";

function UsuarioTable({ usuarios, onEdit, onDelete }) {

    return (

        <Table>

            <thead>
                <tr>

                    <th>Username</th>
                    <th>Rol</th>
                    <th>Acciones</th>

                </tr>
            </thead>

            <tbody>

                {usuarios.map((u) => (

                    <tr key={u.id}>

                        <td>{u.username}</td>
                        <td>{u.rol}</td>

                        <td>

                            <div className="table-actions">

                                <button
                                    className="btn-edit"
                                    onClick={() => onEdit(u)}
                                >
                                    Editar
                                </button>

                                <button
                                    className="btn-delete"
                                    onClick={() => onDelete(u.id)}
                                >
                                    Eliminar
                                </button>

                            </div>

                        </td>

                    </tr>

                ))}

            </tbody>

        </Table>

    );
}

export default UsuarioTable;