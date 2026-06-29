import "./ConductorTable.css";

function ConductorTable({ conductores, onEdit, onDelete }) {

    return (

        <div className="table-container">

            <table className="conductor-table">

                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Rut</th>
                        <th>Nombre</th>
                        <th>Teléfono</th>
                        <th>Licencia</th>
                        <th>Vencimiento</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>

                    {conductores.map((c) => (

                        <tr key={c.id}>

                            <td>{c.id}</td>
                            <td>{c.rut}</td>
                            <td>{c.nombre}</td>
                            <td>{c.telefono}</td>
                            <td>{c.numeroLicencia}</td>
                            <td>{c.fechaVencimientoLicencia}</td>

                            <td>

                                <div className="actions">

                                    <button
                                        className="btn-edit"
                                        onClick={() => onEdit(c)}
                                    >
                                        Editar
                                    </button>

                                    <button
                                        className="btn-delete"
                                        onClick={() => onDelete(c.id)}
                                    >
                                        Eliminar
                                    </button>

                                </div>

                            </td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>

    );

}

export default ConductorTable;